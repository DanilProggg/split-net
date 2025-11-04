package com.split_net.gateway.application.rabbitmq;

import com.split_net.gateway.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventReceiverService {
    @RabbitListener(queues = "#{@gatewayQueue}", autoStartup = "false") // Spring сам найдет очередь
    public void receiveEvent(Event event) {
        // Автоматически используется jsonMessageConverter
        log.debug("Received: {}. Peer: {}, {}",event.getAction(), event.getPeer().getPubkey(), event.getPeer().getIp());
    }
}
