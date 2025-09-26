package com.kridan.split_net.application.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceDto {
    private String uuid;
    private String name;
    private String PublicKey;
    private String ipAddress;
    private Long subnetId;
}
