package com.kridan.split_net.domain.device.ports;

import com.kridan.split_net.domain.device.Device;

import java.util.List;

public interface FindAllDevicesPort {
    List<Device> findAllByEmail (String email);
    List<Device> findAll();
}
