package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import com.kridan.split_net.domain.ports.outbound.db.SaveDevicePort;
import com.kridan.split_net.domain.ports.outbound.wg.CreateWgPeerPort;
import com.kridan.split_net.domain.ports.outbound.wg.CreateWgPrivKeyPort;
import com.kridan.split_net.infrastructure.database.repository.DeviceRepository;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDeviceService implements CreateDeviceUseCase {
    private final FindUserPort findUserPort;
    private final SaveDevicePort saveDevicePort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;
    private final CreateWgPeerPort createWgPeerPort;

    @Override
    public Device createDevice(String userId, CreateDeviceCommand command){
        try {
            User user = findUserPort.findById(UUID.fromString(userId));
            Device device = new Device()
                    .setId(UUID.randomUUID())
                    .setDevicePrivateKey(createWgPrivKeyPort.generatePrivKey())
                    .setName(command.getDeviceName())
                    .setIpAddress(command.getIpAddress())
                    .setAllowedIps(command.getAllowedIps())
                    .setOwner(user);

            //Save to DB
            Device createdDevice = saveDevicePort.save(device);

            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());

            //Adding peer
            createWgPeerPort.createPeer(device);
            return createdDevice;

        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
