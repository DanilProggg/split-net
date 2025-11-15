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
    public void refresh(String gateway_id, String hostname, String publicKey, String wg_url) {
        Gateway gw = findGatewayPort.findById(gateway_id);
        gw.setName(hostname);
        gw.setPublicKey(publicKey);
        gw.setWgUrl(wg_url);
        saveGatewayPort.save(gw);
    }
}
