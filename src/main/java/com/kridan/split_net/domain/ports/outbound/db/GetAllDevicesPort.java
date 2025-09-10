package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;

import java.util.List;

public interface GetAllDevicesPort {
    List<Device> getDevices (User user);
}
