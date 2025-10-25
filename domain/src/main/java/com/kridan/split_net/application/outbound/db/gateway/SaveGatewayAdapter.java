package com.kridan.split_net.application.outbound.db.gateway;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.infrastructure.database.repository.gateway.GatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveGatewayAdapter implements SaveGatewayPort {

    private final GatewayRepository gatewayRepository;

    @Override
    public Gateway save(Gateway gateway) {
        return gatewayRepository.save(gateway);
    }
}
