package com.split_net.gateway.services;

import com.split_net.gateway.domain.Peer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PeerManager {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ConcurrentHashMap<String, ScheduledFuture<?>> peerTimers = new ConcurrentHashMap<>();
    private final String interfaceName = "wg0";


    /**
     * Добавляет пир в WireGuard и создает таймер на удаление с указанным таймаутом
     */
    public void addPeer(Peer peer) {
        String publicKey = peer.getPubkey();

        try {

            long now = System.currentTimeMillis();
            long expirationTime = peer.getExpiredAt().getTime();
            long delayMinutes = (expirationTime - now) / (1000 * 60);

            log.info("Adding peer: {}, timeout: {} minutes", publicKey, delayMinutes);

            // Добавляем пир в WireGuard
            addPeerToWireGuard(peer);

            if (delayMinutes <= 0) {
                log.warn("Peer {} has already expired, removing immediately", publicKey);
                removePeer(publicKey);
                return;
            }

            // Создаем таймер на удаление
            ScheduledFuture<?> timer = scheduler.schedule(() -> {
                removePeer(publicKey);
            }, delayMinutes, TimeUnit.MINUTES);

            // Сохраняем таймер (отменяем предыдущий если был)
            ScheduledFuture<?> existingTimer = peerTimers.put(publicKey, timer);
            if (existingTimer != null) {
                existingTimer.cancel(false);
            }

            log.info("Peer {} added successfully, will be removed in {} minutes", publicKey, delayMinutes);

        } catch (Exception e) {
            log.error("Failed to add peer: {}", publicKey, e);
            throw new RuntimeException("Failed to add peer: " + publicKey, e);
        }
    }

    /**
     * Удаляет пир из WireGuard и отменяет таймер
     */
    public void removePeer(Peer peer) {
        removePeer(peer.getPubkey());
    }

    /**
     * Удаляет пир из WireGuard и отменяет таймер
     */
    public void removePeer(String publicKey) {
        try {
            log.info("Removing peer: {}", publicKey);

            // Удаляем пир из WireGuard
            removePeerFromWireGuard(publicKey);

            // Отменяем таймер
            cancelPeerTimer(publicKey);

            log.info("Peer {} removed successfully", publicKey);

        } catch (Exception e) {
            log.error("Failed to remove peer: {}", publicKey, e);
            throw new RuntimeException("Failed to remove peer: " + publicKey, e);
        }
    }

    private void addPeerToWireGuard(Peer peer) throws Exception {
        List<String> command = new ArrayList<>();
        command.addAll(Arrays.asList("wg", "set", interfaceName, "peer", peer.getPubkey()));

        if (peer.getIp() != null && !peer.getIp().isEmpty()) {
            command.add("allowed-ips");
            command.add(peer.getIp());
        }

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String errorOutput = new String(process.getInputStream().readAllBytes());
            throw new RuntimeException("WireGuard command failed: " + errorOutput);
        }

        // Поднимаем интерфейс
        ensureInterfaceUp();
    }

    private void removePeerFromWireGuard(String publicKey) throws Exception {
        Process process = new ProcessBuilder("wg", "set", interfaceName, "peer", publicKey, "remove")
                .redirectErrorStream(true)
                .start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String errorOutput = new String(process.getInputStream().readAllBytes());
            throw new RuntimeException("Failed to remove peer: " + errorOutput);
        }
    }

    private void ensureInterfaceUp() throws Exception {
        Process process = new ProcessBuilder("ip", "link", "set", "up", "dev", interfaceName)
                .redirectErrorStream(true)
                .start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.warn("Failed to bring interface {} up", interfaceName);
        }
    }

    private void cancelPeerTimer(String publicKey) {
        ScheduledFuture<?> timer = peerTimers.remove(publicKey);
        if (timer != null) {
            timer.cancel(false);
            log.debug("Timer cancelled for peer: {}", publicKey);
        }
    }

    private File createTempKeyFile(String key, String prefix) throws IOException {
        File tempKeyFile = File.createTempFile(prefix, ".tmp");
        tempKeyFile.deleteOnExit();
        Files.write(tempKeyFile.toPath(), key.getBytes(StandardCharsets.UTF_8));
        return tempKeyFile;
    }

    @PreDestroy
    public void shutdown() {
        log.debug("Shutting down PeerManager");
        scheduler.shutdown();
    }
}