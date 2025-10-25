package com.kridan.split_net.domain.device.command;

import lombok.Getter;

@Getter
public class CreateDeviceCommand {
    private String deviceName;
    private String ipAddress;
    private String allowedIps;
}
