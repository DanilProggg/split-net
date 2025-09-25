package com.kridan.split_net.domain.device.usecases;

import com.kridan.split_net.domain.device.command.CreateDeviceCommand;

public interface CreateDeviceUseCase {
    String createDevice(String userId,
                        String deviceName,
                        String ipAddress,
                        Long subnetId
    );
}
