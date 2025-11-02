package com.kridan.split_net.domain.device.usecases;

import java.io.IOException;

public interface CreateDeviceUseCase {
    String createDevice(String userId,
                        String deviceName,
                        String ipAddress
    ) throws IOException, InterruptedException;
}
