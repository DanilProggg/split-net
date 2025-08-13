package com.kridan.split_net.domain.command;

import lombok.Getter;

@Getter
public class CreateDeviceCommand {
    private String deviceName;
    private String ipAddress;
    private String allowedIps;
}
