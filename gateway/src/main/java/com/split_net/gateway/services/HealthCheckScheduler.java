package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthCheckScheduler {

    @Value("${gateway.api}")
    private String apiUrl;

    @Value("${gateway.jwtToken}")
    private String jwtToken;

    private final WebClient webClient;
    private final GatewayState gatewayState;
    private ScheduledExecutorService scheduler;


    public void startHealthChecks() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return; // Уже запущен
        }

        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Запускаем каждую минуту с начальной задержкой 0
        scheduler.scheduleAtFixedRate(() -> {
            if (gatewayState.isHealthCheckEnabled()) {
                log.debug("Health check attempt");
                performHealthCheck();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void performHealthCheck() {
        try {
            webClient.post()
                    .uri(apiUrl+"/api/gateways/health")
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve()
                    .toBodilessEntity()
                    .doOnSuccess(response -> {
                        if (response.getStatusCode().is2xxSuccessful()) {
                            log.debug("Health check OK - Status: {}", response.getStatusCode());
                        } else {
                            log.error("Health check FAILED - Status: {}", response.getStatusCode());
                        }
                    })
                    .doOnError(error -> {
                        log.error("Health check error: {}", error.getMessage());
                    })
                    .subscribe(); // ⬅️ Запускаем подписку
            log.debug("Health check to {}", apiUrl+"/api/gateways/health");

        } catch (Exception e) {
            log.error("Health check error: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}