package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.Device;

import java.util.List;

public interface GetAllDevicesPort {
    List<Device> getDevices (String email);
}
