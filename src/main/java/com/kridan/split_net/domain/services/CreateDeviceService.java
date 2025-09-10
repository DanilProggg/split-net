package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import com.kridan.split_net.domain.ports.inbound.GetConfigUseCase;
import com.kridan.split_net.domain.ports.outbound.GetGlobalConfigPort;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import com.kridan.split_net.domain.ports.outbound.db.SaveDevicePort;
import com.kridan.split_net.domain.ports.outbound.wg.CreateWgPeerPort;
import com.kridan.split_net.domain.ports.outbound.wg.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.ports.outbound.wg.CreateWgPubKeyPort;
import com.kridan.split_net.domain.value.DeviceConfig;
import com.kridan.split_net.infrastructure.database.repository.DeviceRepository;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
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

    private final FindUserPort findUserPort;
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
            User user = findUserPort.findById(UUID.fromString(userId));

            //Device`s private key
            String privateKey = createWgPrivKeyPort.generatePrivKey();

            String deviceConfig = String.format("""
                    [Interface]
                    PrivateKey = %s
                    Address = %s
            
                    [Peer]
                    PublicKey = %s
                    AllowedIPs = %s
                    Endpoint = %s:%d
                    """,
                    privateKey,
                    command.getIpAddress(),

                    (String) Hibernate.unproxy(getConfigUseCase.get("publicKey")),
                    command.getAllowedIps(),
                    (String) Hibernate.unproxy(getConfigUseCase.get("url")),
                    port
            );

            Device device = new Device()
                    .setId(UUID.randomUUID())
                    .setName(command.getDeviceName())
                    .setPublicKey(createWgPubKeyPort.generatePubKey(privateKey))
                    .setIpAddress(command.getIpAddress())
                    .setAllowedIps(command.getAllowedIps())
                    .setOwner(user);

            //Save to DB

            Optional<Device> device1 = user.getDevices().stream()
                    .filter(
                            d -> d.getName().equals(command.getDeviceName())
                    ).findAny();

            if (device1.isPresent()) throw new RuntimeException("Device name already in use");
            Device createdDevice = saveDevicePort.save(device);


            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());

            //Adding peer
            createWgPeerPort.createPeer(device);

            return deviceConfig;

        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
