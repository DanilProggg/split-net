package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.device.value.DeviceCreationResult;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DeviceFactory {
    private final FindUserPort findUserPort;

    public DeviceCreationResult create(String userId,
                                       String name,
                                       String ipAddress,
                                       String allowedIps,
                                       String devicePrivateKey,
                                       String devicePublicKey,
                                       String serverPublicKey,
                                       String serverUri
    ) {
        User user = findUserPort.findById(UUID.fromString(userId));

        Device device = new Device()
                .setId(UUID.randomUUID())
                .setName(name)
                .setPublicKey(devicePublicKey)
                .setIpAddress(ipAddress)
                .setAllowedIps(allowedIps)
                .setOwner(user);

        Optional<Device> device1 = user.getDevices().stream()
                .filter(
                        d -> d.getName().equals(name)
                ).findAny();

        if (device1.isPresent()) throw new RuntimeException("Device name already in use");

        DeviceConfig deviceConfig = new DeviceConfig(
                devicePrivateKey,
                serverPublicKey,
                ipAddress,
                allowedIps,
                serverUri
        );



        return new DeviceCreationResult(device, deviceConfig);

    }
}
