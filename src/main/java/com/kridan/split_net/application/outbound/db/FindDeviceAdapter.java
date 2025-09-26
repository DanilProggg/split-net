package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.device.ports.FindAllDevicesPort;
import com.kridan.split_net.domain.device.ports.FindDevicePort;
import com.kridan.split_net.infrastructure.database.repository.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Device getDevice(String email, String deviceName) {
        User user = findUserPort.findByEmail(email);
        return deviceRepository.findByOwnerAndName(user, deviceName)
                .orElseThrow(()->new RuntimeException("Device with given data not found"));
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
}
