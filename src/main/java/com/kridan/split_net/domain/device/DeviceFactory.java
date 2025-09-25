package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.subnet.Subnet;
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

    public Device create(String userId,
                         String name,
                         String ipAddress,
                         String devicePublicKey,
                         Subnet subnet
                         ) {
        User user = findUserPort.findById(UUID.fromString(userId));

        Device device = new Device()
                .setId(UUID.randomUUID())
                .setName(name)
                .setPublicKey(devicePublicKey)
                .setIpAddress(ipAddress)
                .setSubnet(subnet)
                .setOwner(user);

        Optional<Device> device1 = user.getDevices().stream()
                .filter(
                        d -> d.getName().equals(name)
                ).findAny();

        if (device1.isPresent()) throw new RuntimeException("Device name already in use");

        return device;

    }
}
