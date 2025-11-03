package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQStarter {

    private final GatewayState gatewayState;
    private final RabbitListenerEndpointRegistry rabbitListenerRegistry;

    public void startRabbitMQ() {
        try {
            // Запускаем все RabbitMQ listeners
            rabbitListenerRegistry.getListenerContainers().forEach(container -> {
                if (!container.isRunning()) {
                    container.start();
                }
            });

            gatewayState.setHealthCheckEnabled(true);
            log.debug("RabbitMQ started successfully");

        } catch (Exception e) {
            log.error("Failed to start RabbitMQ: " + e.getMessage());
        }
    }
}