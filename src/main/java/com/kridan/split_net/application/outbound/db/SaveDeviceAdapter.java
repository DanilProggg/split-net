package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.infrastructure.database.repository.device.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveDeviceAdapter implements SaveDevicePort {

    private final DeviceRepository deviceRepository;

    public SaveDeviceAdapter(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }
}
