package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.command.CreateDeviceCommand;

public interface CreateDeviceUseCase {
    void createDevice(String userId, CreateDeviceCommand command);
}
