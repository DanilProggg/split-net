package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.ports.inbound.GetAllDevicesUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDevicesService implements GetAllDevicesUseCase {
    @Override
    public List<Device> devices(String email) {

    }
}
