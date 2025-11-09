package com.kridan.split_net.application.outbound.rabbitmq;

import com.kridan.split_net.domain.device.Peer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public void publishEvent(Peer peer, String action) {

        //Actions: add, delete, update

        Event event = Event.builder()
                .peer(peer)
                .action(action)
                .build();

        rabbitTemplate.convertAndSend("user.events.exchange", "", event);
    }

    // Универсальный метод
    public void publishEvent(Event event) {
        rabbitTemplate.convertAndSend("user.events.exchange", "", event);
    }
}