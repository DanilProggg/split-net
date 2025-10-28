package com.kridan.split_net.domain.gateway.usecases;

public interface RefreshGatewayInfoUseCase {
    void refresh(Long gateway_id, String publicKey, String wg_url);
}
