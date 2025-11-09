package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.application.outbound.rabbitmq.EventPublisherService;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.Peer;
import com.kridan.split_net.domain.device.ports.FindDevicePort;
import com.kridan.split_net.domain.device.usecases.ActivateDeviceUseCase;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivateDeviceService implements ActivateDeviceUseCase {

    private final FindDevicePort findDevicePort;
    private FindAllGatewaysPort findAllGatewaysPort;
    private final EventPublisherService eventPublisherService;

    @Override
    public void activate(String device_id) {

        Device device = findDevicePort.findById(device_id);

        if(device.getLastActivation() == null || isPeriodExpired(device.getLastActivation().toInstant(), 24)){
            //Send event to gateway
            Peer peer = new Peer();
            peer.setIp(device.getIpAddress());
            peer.setPubkey(device.getPublicKey());
            peer.setExpiredAt(Date.from(Instant.now().plus(Duration.ofHours(device.getOwner().getReauthIntervalHours()))));
            eventPublisherService.publishEvent(peer, "add");
        }
    }

    public boolean isPeriodExpired(Instant lastTime, long hours) {
        Instant now = Instant.now();
        return lastTime.plus(Duration.ofHours(hours)).isBefore(now);
    }
}
