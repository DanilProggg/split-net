package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GatewayInitializer {

    @Value("${gateway.api}")
    private final String apiUrl;

    private final WebClient webClient;
    private final GatewayState gatewayState;
    private final HealthCheckScheduler healthCheckScheduler;
    private final RabbitMQStarter rabbitMQStarter;

    public void initialize() {
        //Выполняем первоначальную инициализацию
        performInitialization();

        //Помечаем как инициализирован
        gatewayState.setInitialized(true);

        //Запускаем health check каждую минуту
        healthCheckScheduler.startHealthChecks();

        //Включаем RabbitMQ
        rabbitMQStarter.startRabbitMQ();
    }

    private void performInitialization() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ResponseEntity<Void> response = webClient.get()
                        .uri(apiUrl + "/api/gateways/init")
                        .retrieve()
                        .toBodilessEntity()
                        .block(); // Блокируем до получения ответа

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("✅ Initialization successful");
                    return; // Выходим из функции только при успехе
                } else {
                    System.out.println("❌ Initialization failed, status: " + response.getStatusCode());
                }

            } catch (Exception e) {
                System.out.println("❌ Initialization error: " + e.getMessage());
            }

            // Ждем 10 секунд перед следующей попыткой
            try {
                System.out.println("🕒 Retrying in 10 seconds...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("🚫 Initialization interrupted");
                throw new RuntimeException("Initialization interrupted");
            }
        }

    }
}
