package com.kridan.split_net.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@Slf4j
public class WireGuardInitializer implements CommandLineRunner {

    @Value("${wg.interface:wg0}")
    private String interfaceName;

    private String privateKey;

    @Value("${wg.ip:100.64.0.0/10}")
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

            // Генерируем 32 random bytes и кодируем в base64
            byte[] keyBytes = new byte[32];
            new SecureRandom().nextBytes(keyBytes);
            privateKey = Base64.getEncoder().encodeToString(keyBytes);

            // Сохраняем ключ в файл
            try (FileWriter writer = new FileWriter(keyFile)) {
                writer.write(privateKey);
            }

            log.info("Private key generated and saved to " + keyFile.getAbsolutePath());
        } else {
            // Если файл есть, читаем его содержимое
            privateKey = Files.readString(keyFile.toPath()).trim();
            log.info("Private key exists, using existing one.");
        }

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
    }
}