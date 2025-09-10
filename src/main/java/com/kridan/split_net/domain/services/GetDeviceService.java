package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.ports.inbound.GetDeviceUseCase;
import com.kridan.split_net.domain.ports.outbound.db.GetDevicePort;
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
