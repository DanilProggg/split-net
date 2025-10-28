package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.GatewayConfig.GatewayConfig;

public interface GenerateConfigUseCase {
    GatewayConfig generate(Long gateway_id);
}
