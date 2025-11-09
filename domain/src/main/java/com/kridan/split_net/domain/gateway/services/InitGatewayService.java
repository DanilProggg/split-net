package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.InitGatewayUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitGatewayService implements InitGatewayUseCase {

    private final FindGatewayPort findGatewayPort;
    private final SaveGatewayPort saveGatewayPort;

    @Override
    public Gateway init(Long gateway_id, String pubkey, String url) {
        Gateway gateway = findGatewayPort.findById(gateway_id);
        gateway.setWgUrl(url);
        gateway.setPublicKey(pubkey);
        return saveGatewayPort.save(gateway);
    }
}
