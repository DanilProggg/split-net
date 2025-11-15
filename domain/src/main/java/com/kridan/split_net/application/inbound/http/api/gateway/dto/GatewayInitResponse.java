package com.kridan.split_net.application.inbound.http.api.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class GatewayInitResponse {
    private String ip;
}

