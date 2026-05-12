package com.kridan.split_net.application.inbound.http.api.device.dto;

import com.kridan.split_net.domain.device.Device;

import java.util.Date;

public record DeviceDetailRecord(
        String uuid,
        String name,
        String publicKey,
        String ipAddress,
        Date lastActivation,
        String userId
) {
    public static DeviceDetailRecord from(Device device) {
        return new DeviceDetailRecord(
                device.getDeviceId().toString(),
                device.getName(),
                device.getPublicKey(),
                device.getIpAddress(),
                device.getLastActivation(),
                device.getOwner().getUserId().toString()
        );
    }
}
