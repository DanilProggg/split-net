package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.usecases.GetDeviceUseCase;
import com.kridan.split_net.domain.device.ports.GetDevicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDeviceService implements GetDeviceUseCase {

    private final GetDevicePort getDevicePort;

    @Override
    public Device getDevice(String email, String deviceName) {
        return getDevicePort.getDevice(email, deviceName);
    }
}
