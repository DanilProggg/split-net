package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import com.kridan.split_net.domain.ports.outbound.db.GetAllDevicesPort;
import com.kridan.split_net.domain.ports.outbound.db.GetDevicePort;
import com.kridan.split_net.infrastructure.database.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindDeviceAdapter implements GetAllDevicesPort, GetDevicePort {

    private final DeviceRepository deviceRepository;
    private final FindUserPort findUserPort;


    @Override
    public List<Device> getDevices(String email) {
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
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }
}
