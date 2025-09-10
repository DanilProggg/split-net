package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.ports.inbound.GetAllDevicesUseCase;
import com.kridan.split_net.domain.ports.outbound.db.GetAllDevicesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllDevicesService implements GetAllDevicesUseCase {

    private final GetAllDevicesPort getAllDevicesPort;

    @Override
    public List<Device> devices(String email) {
        return getAllDevicesPort.getDevices(email);
    }
}
