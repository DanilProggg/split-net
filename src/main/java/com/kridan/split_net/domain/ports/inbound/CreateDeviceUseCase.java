package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;

public interface CreateDeviceUseCase {
    Device createDevice(String userId, CreateDeviceCommand command);
}
