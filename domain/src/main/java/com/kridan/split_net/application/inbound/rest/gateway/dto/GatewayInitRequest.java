package com.kridan.split_net.application.inbound.rest.gateway.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GatewayInitRequest {
    private String wg_url;
}
