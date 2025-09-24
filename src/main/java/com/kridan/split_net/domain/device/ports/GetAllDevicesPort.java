package com.kridan.split_net.domain.device.ports;

import com.kridan.split_net.domain.device.Device;

import java.util.List;

public interface GetAllDevicesPort {
    List<Device> getDevices (String email);
    List<Device> getAll();
}
