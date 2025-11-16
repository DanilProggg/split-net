package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import com.split_net.gateway.services.dto.GatewayInitResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @Value("${gateway.name}")
    private String gatewayName;

    private final WebClient webClient;
    private final GatewayState gatewayState;
    private final HealthCheckScheduler healthCheckScheduler;
    private final RabbitMQStarter rabbitMQStarter;
    private final WireguardService wireguardService;

    private final ConfigService configService;


    @EventListener(ApplicationReadyEvent.class)
    public void initialize() throws IOException, InterruptedException {
        //Выполняем первоначальную инициализацию

        log.debug("WG URL: {}", wg_url);
        log.debug("NAME: {}", gatewayName);

        wireguardService.genKeys();

        performInitialization();
        log.info("Http init query done");

        // wireguardService.setup();
        log.debug("Wireguard initialized");

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
                        "gatewayName", gatewayName,
                        "gatewayUrl", wg_url,
                        "publicKey", configService.getValue("publicKey")
                );

                GatewayInitResponse response = webClient.post()
                        .uri(apiUrl + "/api/gateway/init")
                        .header("Authorization", "Bearer " + jwtToken)
                        .bodyValue(requestBody)
                        .retrieve()
                        .onStatus(status -> !status.is2xxSuccessful(),
                                clientResponse -> Mono.error(new RuntimeException("Gateway init failed: " + clientResponse.statusCode())))

                        .bodyToMono(GatewayInitResponse.class)
                        .block(); // Блокируем до получения ответа


                log.info("Received response: {}", response);

                if (response != null && response.getIp() != null) {
                    configService.save("ip", response.getIp());
                    log.debug("Initialization successful with IP: {}", response.getIp());
                    break; // выходим из цикла только если IP получен
                } else {
                    log.warn("Initialization response received but IP is null, retrying...");
                }

            } catch (Exception e) {
                log.error("Initialization error: " + e.getMessage());
            }

            // Ждем 10 секунд перед следующей попыткой
            try {
                log.debug("🕒 Retrying in 10 seconds...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Initialization interrupted");
                throw new RuntimeException("Initialization interrupted");
            }
        }

    }
}
