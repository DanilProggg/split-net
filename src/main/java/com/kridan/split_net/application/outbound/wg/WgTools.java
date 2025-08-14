package com.kridan.split_net.application.outbound.wg;

import com.kridan.split_net.domain.ports.outbound.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.ports.outbound.CreateWgPubKeyPort;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
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
    public String generatePubKey(String base64PrivateKey) throws IOException {
        Process process = new ProcessBuilder("echo", "\""+base64PrivateKey+"\"", "|", "wg", "pubkey")
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
}
