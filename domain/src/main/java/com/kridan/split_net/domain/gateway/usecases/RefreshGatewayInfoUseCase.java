package com.kridan.split_net.domain.gateway.usecases;

public interface RefreshGatewayInfoUseCase {
    void refresh(String gateway_id, String hostname, String publicKey, String wg_url);
}
