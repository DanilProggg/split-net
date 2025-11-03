package com.kridan.split_net.application.inbound.http.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateDeviceRequest {
    private String deviceName;
    private String ipAddress;
    private Long subnetId;
}
