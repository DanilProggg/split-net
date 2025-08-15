package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.Device;

public interface SaveDevicePort {
    Device save(Device device);
}
