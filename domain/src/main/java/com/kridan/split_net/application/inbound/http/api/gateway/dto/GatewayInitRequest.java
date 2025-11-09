package com.kridan.split_net.application.inbound.http.api.gateway.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GatewayInitRequest {
    private String gatewayUrl;
    private String publicKey;
}
