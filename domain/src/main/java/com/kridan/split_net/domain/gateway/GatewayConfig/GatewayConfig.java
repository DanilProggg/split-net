package com.kridan.split_net.domain.gateway.GatewayConfig;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GatewayConfig {
    // public key / ip addr
    private Map<String, String> peers;
}
