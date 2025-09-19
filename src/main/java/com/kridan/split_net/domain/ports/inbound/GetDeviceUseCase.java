package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;

public interface GetDeviceUseCase {
    Device getDevice(String email, String deviceName);
}
