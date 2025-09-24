package com.kridan.split_net.domain.device.value;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.DeviceConfig;


public record DeviceCreationResult(
        Device device,
        DeviceConfig deviceConfig
) {
}
