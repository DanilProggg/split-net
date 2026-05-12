package com.kridan.split_net.application.inbound.http.api.device.dto;

import com.kridan.split_net.domain.device.Device;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceDto {
    private String uuid;
    private String name;
    private String PublicKey;
    private String ipAddress;


}
