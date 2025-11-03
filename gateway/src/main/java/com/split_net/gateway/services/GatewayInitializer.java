package com.split_net.gateway.services;

import com.split_net.gateway.config.JwtConfig;
import com.split_net.gateway.domain.Config;
import com.split_net.gateway.domain.GatewayState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayInitializer {

    @Value("${gateway.wg.url}")
    private String wg_url;


    @Value("${gateway.api}")
    private String apiUrl;

    @Value("${gateway.jwtToken}")
    private String jwtToken;

    private final WebClient webClient;
    private final GatewayState gatewayState;
    private final HealthCheckScheduler healthCheckScheduler;
    private final RabbitMQStarter rabbitMQStarter;
    private final WireguardService wireguardService;

    private final ConfigService configService;


    @EventListener(ApplicationReadyEvent.class)
    public void initialize() throws IOException, InterruptedException {
        //Выполняем первоначальную инициализацию


        wireguardService.setup();
        log.debug("Wireguard initialized");

        performInitialization();
        log.debug("Http init query done");

        //Помечаем как инициализирован
        gatewayState.setInitialized(true);

        //Запускаем health check каждую минуту
        healthCheckScheduler.startHealthChecks();
        log.debug("Health checker is started");

        //Включаем RabbitMQ
        rabbitMQStarter.startRabbitMQ();
    }

    private void performInitialization() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                Map<String, String> requestBody = Map.of(
                        "gatewayUrl", wg_url,
                        "publicKey", configService.getValue("publicKey")
                );

                ResponseEntity<Void> response = webClient.post()
                        .uri(apiUrl + "/api/gateways/init")
                        .header("Authorization", "Bearer " + jwtToken)
                        .bodyValue(requestBody)
                        .retrieve()
                        .toBodilessEntity()
                        .block(); // Блокируем до получения ответа

                if (response.getStatusCode().is2xxSuccessful()) {
                    log.debug("✅ Initialization successful");
                    return; // Выходим из функции только при успехе
                } else {
                    log.debug("❌ Initialization failed, status: " + response.getStatusCode());
                }

            } catch (Exception e) {
                log.error("❌ Initialization error: " + e.getMessage());
            }

            // Ждем 10 секунд перед следующей попыткой
            try {
                log.debug("🕒 Retrying in 10 seconds...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("🚫 Initialization interrupted");
                throw new RuntimeException("Initialization interrupted");
            }
        }

    }
}
