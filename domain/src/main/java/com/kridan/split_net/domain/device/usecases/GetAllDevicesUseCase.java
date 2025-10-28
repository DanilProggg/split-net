package com.kridan.split_net.domain.device.usecases;

import com.kridan.split_net.domain.device.Device;

import java.util.List;

public interface GetAllDevicesUseCase {
    List<Device> getAllDevices (String email);
}
