package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.gateway.Gateway;

public interface InitGatewayUseCase {
    Gateway init(Long gateway_id, String pubkey, String url);
}
