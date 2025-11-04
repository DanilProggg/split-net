package com.kridan.split_net.domain.device.usecases;

import com.kridan.split_net.domain.device.Device;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CreateDeviceUseCase {
    Device createDevice(String userId,
                                          String name,
                                          String pubkey
    ) throws IOException, InterruptedException;
}
