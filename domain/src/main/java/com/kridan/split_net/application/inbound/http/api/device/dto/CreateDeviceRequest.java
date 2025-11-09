package com.kridan.split_net.application.inbound.http.api.device.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateDeviceRequest {
    private String deviceName;
    private String pubkey;
}
