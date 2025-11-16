package com.split_net.gateway.services;

import com.split_net.gateway.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.split_net.gateway.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class WireguardService {

    @Value("${gateway.wg.url}")
    private String wg_url;

    private final ConfigService configService;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;


    public void genKeys() throws IOException, InterruptedException {
        String privateKey;

        File keyFile = new File("/etc/vpn/keys/wg0.key");

        // key check
        if (!keyFile.exists()) {
            log.info("Private key not found, generating new one...");

            keyFile.getParentFile().mkdirs();

            privateKey = createWgPrivKeyPort.generatePrivKey();

            // Сохраняем ключ в файл
            try (FileWriter writer = new FileWriter(keyFile)) {
                writer.write(privateKey);
            }

            log.info("Private key generated and saved to " + keyFile.getAbsolutePath());

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
        configService.save("privateKey", privateKey);
        configService.save("publicKey", createWgPubKeyPort.generatePubKey(privateKey));
    }

    public void setup() throws IOException, InterruptedException {

//        String ip = configService.getValue("ip");
//        if (ip == null || ip.isBlank()) {
//            throw new IllegalStateException("IP address is not set in configService");
//        }
//
//        // Create interface
//        new ProcessBuilder("ip", "link", "add", "wg0", "type", "wireguard")
//                .inheritIO()
//                .start()
//                .waitFor();
//
//        // Configure private key and port
//        new ProcessBuilder("wg", "set", "wg0",
//                "listen-port", String.valueOf(wg_url.split(":")[1].trim()),
//                "private-key", "/etc/vpn/keys/wg0.key")
//                .inheritIO()
//                .start()
//                .waitFor();
//
//        // Set IP address
//        new ProcessBuilder("ip", "addr", "add", ip, "dev", "wg0")
//                .inheritIO()
//                .start()
//                .waitFor();
//
//
//        // Interface link up
//        new ProcessBuilder("ip", "link", "set", "up", "dev", "wg0")
//                .inheritIO()
//                .start()
//                .waitFor();
//
//        log.info("WireGuard interface wg0 created succesful.");

    }

}
