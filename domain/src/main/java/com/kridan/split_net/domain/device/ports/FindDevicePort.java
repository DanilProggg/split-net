package com.kridan.split_net.domain.device.ports;

import com.kridan.split_net.domain.device.Device;

public interface FindDevicePort {
    Device findByOwnerAndId (String user_email, String device_uuid);
    Device findById(String device_uuid);
}
