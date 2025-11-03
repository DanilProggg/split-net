package com.split_net.gateway.services;

import com.split_net.gateway.domain.GatewayState;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQStarter {

    private final GatewayState gatewayState;
    private final RabbitListenerEndpointRegistry rabbitListenerRegistry;

    public RabbitMQStarter(GatewayState gatewayState,
                           RabbitListenerEndpointRegistry rabbitListenerRegistry) {
        this.gatewayState = gatewayState;
        this.rabbitListenerRegistry = rabbitListenerRegistry;
    }

    public void startRabbitMQ() {
        try {
            // Запускаем все RabbitMQ listeners
            rabbitListenerRegistry.getListenerContainers().forEach(container -> {
                if (!container.isRunning()) {
                    container.start();
                }
            });

            gatewayState.setHealthCheckEnabled(true);
            System.out.println("RabbitMQ started successfully");

        } catch (Exception e) {
            System.out.println("Failed to start RabbitMQ: " + e.getMessage());
        }
    }
}