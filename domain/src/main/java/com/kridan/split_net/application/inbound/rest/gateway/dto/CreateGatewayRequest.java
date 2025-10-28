package com.kridan.split_net.application.inbound.rest.gateway.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateGatewayRequest {
    private String name;
    private Long site_id;
    private String ipAddress;
}
