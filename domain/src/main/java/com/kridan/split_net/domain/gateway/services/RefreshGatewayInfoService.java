package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.RefreshGatewayInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshGatewayInfoService implements RefreshGatewayInfoUseCase {

    private final FindGatewayPort findGatewayPort;
    private final SaveGatewayPort saveGatewayPort;

    @Override
    public void refresh(Long gateway_id, String publicKey, String wg_url) {
        Gateway gw = findGatewayPort.findById(gateway_id);
        gw.setPublicKey(publicKey);
        gw.setWg_url(wg_url);
        saveGatewayPort.save(gw);
    }
}
