package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.gateway.Gateway;

public interface InitGatewayUseCase {
    Gateway init(String gateway_id, String hostname, String pubkey, String url);
}
