package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.application.outbound.rabbitmq.EventPublisherService;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.DeviceConfig;
import com.kridan.split_net.domain.device.DeviceConfigGenerator;
import com.kridan.split_net.domain.device.DeviceFactory;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPeerPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDeviceService implements CreateDeviceUseCase {

    private final DeviceFactory deviceFactory;
    private final DeviceConfigGenerator deviceConfigGenerator;
    private final SaveDevicePort saveDevicePort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final CreateWgPeerPort createWgPeerPort;
    private final FindUserPort findUserPort;
    private final EventPublisherService eventPublisherService;

    @Override
    public String createDevice(String userId,
                               String deviceName,
                               String ipAddress) throws IOException, InterruptedException {
        try {

            //Device`s private/public key
            String devicePrivateKey = createWgPrivKeyPort.generatePrivKey();
            String devicePublicKey = createWgPubKeyPort.generatePubKey(devicePrivateKey);

            Device device = deviceFactory.create(
                    findUserPort.findById(UUID.fromString(userId)),
                    deviceName,
                    ipAddress,
                    devicePublicKey
            );

            Device createdDevice = saveDevicePort.save(device);

            //Send event to gateway
            if(!device.getOwner().isRequiredLogin()){
                Map<String, String> peer = new HashMap<>();
                peer.put(device.getPublicKey(), device.getIpAddress());
                eventPublisherService.publishEvent(peer, "add");
            }

            //Generate configuration
            DeviceConfig deviceConfig = deviceConfigGenerator.generate(devicePrivateKey, ipAddress);


            //Logging
            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());

            //Adding peer
            createWgPeerPort.createPeer(createdDevice);

            return deviceConfig.toWireguardFormat();

        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
