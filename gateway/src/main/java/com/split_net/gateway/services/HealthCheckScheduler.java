package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

            log.debug("Health check to {}", apiUrl+"/api/gateway/health");
            ResponseEntity<Void> response = webClient.post()
                    .uri(apiUrl+"/api/gateway/health")
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve()
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(10));

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Health check SUCCESS - Status: {}", response.getStatusCode());
            } else {
                log.warn("Health check HTTP WARNING - Status: {}", response.getStatusCode());
            }


        } catch (WebClientResponseException e) {
            // Ошибки HTTP (4xx, 5xx)
            log.warn("Health check HTTP ERROR - Status: {}, Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());

        } catch (Exception e) {
            // Все остальные ошибки
            log.error("Health check UNEXPECTED ERROR: {}", e.getMessage());
            // Для отладки можно добавить stack trace на DEBUG уровне
            log.debug("Stack trace for health check error:", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}