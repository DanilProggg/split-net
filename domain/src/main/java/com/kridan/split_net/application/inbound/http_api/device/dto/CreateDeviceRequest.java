package com.kridan.split_net.application.inbound.http_api.device.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateDeviceRequest {
    private String deviceName;
    private String pubkey;
}
