package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.DeviceFactory;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDeviceService implements CreateDeviceUseCase {

    private final DeviceFactory deviceFactory;
    private final SaveDevicePort saveDevicePort;
    private final FindUserPort findUserPort;

    @Override
    public Device createDevice(String userId,
                               String name,
                               String pubkey) throws IOException, InterruptedException {
        try {

            String ipAddress = "100.64.100.1";

            //Make dhcp for IP
            Device device = deviceFactory.create(
                    findUserPort.findById(UUID.fromString(userId)),
                    name,
                    ipAddress,
                    pubkey
            );

            Device createdDevice = saveDevicePort.save(device);

            //Logging
            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());


            return createdDevice;
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
