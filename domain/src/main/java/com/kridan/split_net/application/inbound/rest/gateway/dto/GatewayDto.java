package com.kridan.split_net.application.inbound.rest.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class GatewayDto {
    private Long gateway_id;
    private String name;
    private String wg_url;
    private String publicKey;
    private String ipAddress;
    private Long site_id;
}
