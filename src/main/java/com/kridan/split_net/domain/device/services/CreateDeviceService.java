package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.DeviceFactory;
import com.kridan.split_net.domain.device.command.CreateDeviceCommand;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.value.DeviceCreationResult;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.globalConfig.usecases.GetConfigUseCase;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPeerPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDeviceService implements CreateDeviceUseCase {

    private final DeviceFactory deviceFactory;
    private final SaveDevicePort saveDevicePort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final CreateWgPeerPort createWgPeerPort;
    private final GetConfigUseCase getConfigUseCase;
    @Value("${wg.port}")
    private int port;

    @Override
    public String createDevice(String userId, CreateDeviceCommand command){
        try {

            //Device`s private/public key
            String devicePrivateKey = createWgPrivKeyPort.generatePrivKey();
            String devicePublicKey = createWgPubKeyPort.generatePubKey(devicePrivateKey);

            DeviceCreationResult deviceCreationResult = deviceFactory.create(
                    userId,
                    command.getDeviceName(),
                    command.getIpAddress(),
                    command.getAllowedIps(),
                    devicePrivateKey,
                    devicePublicKey,
                    getConfigUseCase.get("publicKey").getValue(),
                    getConfigUseCase.get("url").getValue()
            );

            Device createdDevice = saveDevicePort.save(deviceCreationResult.device());

            //Logging
            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());

            //Adding peer
            createWgPeerPort.createPeer(createdDevice);

            return deviceCreationResult.deviceConfig().generateConfig();

        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
