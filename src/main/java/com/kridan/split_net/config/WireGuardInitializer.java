package com.kridan.split_net.config;

import com.kridan.split_net.application.outbound.db.FindDeviceAdapter;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.globalConfig.usecases.UpdateConfigUseCase;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPeerPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
@Slf4j
@RequiredArgsConstructor
public class WireGuardInitializer implements CommandLineRunner {

    private final UpdateConfigUseCase updateConfigUseCase;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final FindDeviceAdapter deviceAdapter;
    private final CreateWgPeerPort createWgPeerPort;

    @Value("${wg.interface:wg0}")
    private String interfaceName;

    private String privateKey;

    @Value("${wg.ip:100.64.0.1/10}")
    private String ipAddress;

    @Value("${wg.listenPort}")
    private int listenPort;


    @Override
    public void run(String... args) throws Exception {

        File keyFile = new File("/etc/vpn/keys/wg0.key");

        // key check
        if (!keyFile.exists()) {
            log.info("Private key not found, generating new one...");

            keyFile.getParentFile().mkdirs();

            try {
                // Генерация ключа через shell
                Process process = new ProcessBuilder("wg", "genkey")
                        .redirectErrorStream(true)
                        .start();

                privateKey = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new RuntimeException("Failed to generate WireGuard private key, exit code: " + exitCode);
                }

                // Сохраняем ключ в файл
                try (FileWriter writer = new FileWriter(keyFile)) {
                    writer.write(privateKey);
                }



                log.info("Private key generated and saved to " + keyFile.getAbsolutePath());

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error generating WireGuard key", e);
            }

        } else {
            // Если файл существует, читаем его содержимое
            try {
                privateKey = Files.readString(keyFile.toPath()).trim();

                log.info("Private key exists, using existing one.");
            } catch (IOException e) {
                throw new RuntimeException("Error reading existing WireGuard key", e);
            }
        }

        //Save to global config
        updateConfigUseCase.update("privateKey", privateKey);
        updateConfigUseCase.update("publicKey", createWgPubKeyPort.generatePubKey(privateKey));

        // Create interface
        new ProcessBuilder("ip", "link", "add", interfaceName, "type", "wireguard")
                .inheritIO()
                .start()
                .waitFor();

        // Configure private key and port
        new ProcessBuilder("wg", "set", interfaceName,
                "listen-port", String.valueOf(listenPort),
                "private-key", "/etc/vpn/keys/wg0.key")
                .inheritIO()
                .start()
                .waitFor();

        // Set IP address
        new ProcessBuilder("ip", "addr", "add", ipAddress, "dev", interfaceName)
                .inheritIO()
                .start()
                .waitFor();


        // Interface link up
        new ProcessBuilder("ip", "link", "set", "up", "dev", interfaceName)
                .inheritIO()
                .start()
                .waitFor();

        log.info("WireGuard interface " + interfaceName + " created succesful.");

        //Peer recovering
        for (Device device : deviceAdapter.findAll()) {
            createWgPeerPort.createPeer(device);
        }
    }
}