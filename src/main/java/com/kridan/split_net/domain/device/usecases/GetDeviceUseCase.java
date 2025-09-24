package com.kridan.split_net.domain.device.usecases;

import com.kridan.split_net.domain.device.Device;

public interface GetDeviceUseCase {
    Device getDevice(String email, String deviceName);
}
