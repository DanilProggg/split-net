package com.kridan.split_net.domain.device.ports;

import com.kridan.split_net.domain.device.Device;

public interface FindDevicePort {
    Device getDevice (String email, String deviceName);
}
