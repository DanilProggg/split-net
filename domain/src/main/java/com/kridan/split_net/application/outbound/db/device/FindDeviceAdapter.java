package com.kridan.split_net.application.outbound.db.device;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.device.ports.FindAllDevicesPort;
import com.kridan.split_net.domain.device.ports.FindDevicePort;
import com.kridan.split_net.infrastructure.database.repository.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindDeviceAdapter implements FindAllDevicesPort, FindDevicePort {

    private final DeviceRepository deviceRepository;
    private final FindUserPort findUserPort;

    @Override
    public List<Device> findAllByEmail(String email) {
        User user = findUserPort.findByEmail(email);
        return deviceRepository.findAllByOwner(user);
    }

    @Override
    public Device findByOwnerAndId(String email, String uuid) {
        User user = findUserPort.findByEmail(email);

        return deviceRepository.findByOwnerAndId(user, UUID.fromString(uuid))
                .orElseThrow(()->new DeviceNotFoundException("Device with given data not found"));
    }

    @Override
    public Device findById(String device_uuid) {
        return deviceRepository.findById(UUID.fromString(device_uuid))
                .orElseThrow(()->new DeviceNotFoundException("Device with given UUID not found"));

    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
}
