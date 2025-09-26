package com.kridan.split_net.application.inbound.rest.dto;

import lombok.Getter;

@Getter
public class CreateDeviceRequest {
    private String userId;
    private String deviceName;
    private String ipAddress;
    private Long subnetId;
}
