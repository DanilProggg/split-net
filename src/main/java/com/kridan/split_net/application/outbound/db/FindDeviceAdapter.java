package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.GetAllDevicesPort;
import com.kridan.split_net.infrastructure.database.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindDeviceAdapter implements GetAllDevicesPort {

    private final DeviceRepository deviceRepository;

    @Override
    public List<Device> getDevices(User user) {
        return deviceRepository.findAllByOwner(user);
    }
}
