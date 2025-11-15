package com.kridan.split_net.application.inbound.http.api.gateway.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateGatewayRequest {
    private Long site_id;
}
