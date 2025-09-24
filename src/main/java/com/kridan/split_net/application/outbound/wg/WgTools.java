package com.kridan.split_net.application.outbound.wg;

import com.kridan.split_net.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPubKeyPort;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.stream.Collectors;

@Component
public class WgTools implements CreateWgPrivKeyPort, CreateWgPubKeyPort {

    @Override
    public String generatePrivKey() throws IOException {
        Process process = new ProcessBuilder("wg", "genkey")
                .redirectErrorStream(true) // объединяем stdout и stderr
                .start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String privateKey = reader.lines().collect(Collectors.joining("\n")).trim();
            int exitCode = process.waitFor();
            if (exitCode != 0 || privateKey.isEmpty()) {
                throw new RuntimeException("Failed to generate WireGuard private key");
            }
            return privateKey;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generatePubKey(String base64PrivateKey) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("wg", "pubkey")
                .redirectErrorStream(true)
                .start();

        // Передаем приватный ключ в stdin процесса
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(base64PrivateKey); // ключ без кавычек
            writer.flush();
        }

        // Читаем stdout процесса — там публичный ключ
        String pubKey;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            pubKey = reader.readLine();
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Ошибка при генерации публичного ключа, код выхода: " + exitCode);
        }

        return pubKey;
    }
}
