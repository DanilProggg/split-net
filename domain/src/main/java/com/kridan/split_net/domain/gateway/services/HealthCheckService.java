package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.HealthCheckUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class HealthCheckService implements HealthCheckUseCase {

    private final FindGatewayPort findGatewayPort;
    private final SaveGatewayPort saveGatewayPort;

    @Override
    public void lastSeenUpdate(String gateway_id) {
        Gateway gateway = findGatewayPort.findById(gateway_id);
        gateway.setLastSeen(new Date());
        saveGatewayPort.save(gateway);
    }
}
