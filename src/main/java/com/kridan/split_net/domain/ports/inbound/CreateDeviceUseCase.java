package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.value.DeviceConfig;

public interface CreateDeviceUseCase {
    String createDevice(String userId, CreateDeviceCommand command);
}
