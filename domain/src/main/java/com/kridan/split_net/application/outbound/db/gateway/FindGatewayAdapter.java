package com.kridan.split_net.application.outbound.db.gateway;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.infrastructure.database.repository.gateway.GatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindGatewayAdapter implements FindGatewayPort, FindAllGatewaysPort {

    private final GatewayRepository gatewayRepository;

    @Override
    public Gateway findById(Long id) {
        return gatewayRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Gateway not found")
        );
    }

    @Override
    public Gateway findByName(String name) {
        return gatewayRepository.findByName(name).orElseThrow(
                ()->new RuntimeException("Gateway not found")
        );
    }

    @Override
    public List<Gateway> findAll() {
        return gatewayRepository.findAll();
    }
}
