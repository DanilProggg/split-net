package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.model.Device;

import java.util.List;

public interface GetAllDevicesUseCase {
    List<Device> devices (String email);
}
