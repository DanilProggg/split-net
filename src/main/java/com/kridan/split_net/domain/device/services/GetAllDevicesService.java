package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.usecases.GetAllDevicesUseCase;
import com.kridan.split_net.domain.device.ports.FindAllDevicesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllDevicesService implements GetAllDevicesUseCase {

    private final FindAllDevicesPort findAllDevicesPort;

    @Override
    public List<Device> getAllDevices(String email) {
        return findAllDevicesPort.findAllByEmail(email);
    }
}
