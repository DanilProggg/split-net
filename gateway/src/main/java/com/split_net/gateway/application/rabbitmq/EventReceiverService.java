package com.split_net.gateway.application.rabbitmq;

import com.split_net.gateway.domain.Event;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventReceiverService {
    @RabbitListener(queues = "#{@gatewayQueue}", autoStartup = "false") // Spring сам найдет очередь
    public void receiveEvent(Event event) {
        // Автоматически используется jsonMessageConverter
        System.out.println("Received: " + event.getAction());
    }
}
