package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DeviceFactory {

    public Device create(User user,
                         String ipAddress,
                         String name,
                         String devicePublicKey
                         ) {

        Device device = new Device()
                .setId(UUID.randomUUID())
                .setPublicKey(devicePublicKey)
                .setIpAddress(ipAddress)
                .setOwner(user);

        /*Optional<Device> device1 = user.getDevices().stream()
                .filter(
                        d -> d.getName().equals(name)
                ).findAny();

        if (device1.isPresent()) throw new RuntimeException("Device name already in use");*/

        return device;

    }
}
