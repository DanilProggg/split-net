package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import com.kridan.split_net.domain.ports.outbound.CreateWgPeerPort;
import com.kridan.split_net.domain.ports.outbound.CreateWgPrivKeyPort;
import com.kridan.split_net.infrastructure.database.repository.DeviceRepository;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateDeviceService implements CreateDeviceUseCase {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;
    private final CreateWgPeerPort createWgPeerPort;

    public CreateDeviceService(UserRepository userRepository, DeviceRepository deviceRepository, CreateWgPrivKeyPort createWgPrivKeyPort, CreateWgPeerPort createWgPeerPort) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.createWgPrivKeyPort = createWgPrivKeyPort;
        this.createWgPeerPort = createWgPeerPort;
    }

    @Override
    public void createDevice(String userId, CreateDeviceCommand command){
        try {
            User user = userRepository.getReferenceById(UUID.fromString(userId));
            Device device = new Device()
                    .setId(UUID.randomUUID())
                    .setDevicePrivateKey(createWgPrivKeyPort.generatePrivKey())
                    .setName(command.getDeviceName())
                    .setIpAddress(command.getIpAddress())
                    .setAllowedIps(command.getAllowedIps())
                    .setOwner(user);

            //Сохранение в БД
            deviceRepository.save(device);

            //Добавление пира после создания
            createWgPeerPort.createPeer(device);

        } catch (Exception e){

        }
    }
}
