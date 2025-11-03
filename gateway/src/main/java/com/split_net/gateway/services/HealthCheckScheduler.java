package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class HealthCheckScheduler {

    private final WebClient webClient;
    private final GatewayState gatewayState;
    private ScheduledExecutorService scheduler;

    public HealthCheckScheduler(GatewayState gatewayState) {
        this.webClient = WebClient.builder().build();
        this.gatewayState = gatewayState;
    }

    public void startHealthChecks() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return; // Уже запущен
        }

        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Запускаем каждую минуту с начальной задержкой 0
        scheduler.scheduleAtFixedRate(() -> {
            if (gatewayState.isHealthCheckEnabled()) {
                performHealthCheck();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void performHealthCheck() {
        try {
            webClient.get()
                    .uri("http://your-server:8080/health")
                    .retrieve()
                    .toBodilessEntity()
                    .subscribe(
                            response -> System.out.println("Health check OK"),
                            error -> System.out.println("Health check FAILED: " + error.getMessage())
                    );

        } catch (Exception e) {
            System.out.println("Health check error: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}