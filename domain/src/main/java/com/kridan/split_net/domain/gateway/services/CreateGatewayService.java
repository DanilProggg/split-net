package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.CreateGatewayUseCase;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateGatewayService implements CreateGatewayUseCase {

    private final SaveGatewayPort saveGatewayPort;
    private final FindSitePort findSitePort;


    @Override
    public Gateway create(String gatewayId, String name, Long siteId) {


        Gateway gateway = Gateway.builder()
                .gatewayId(UUID.fromString(gatewayId))
                .name(name)
                .ipAddress("100.64.0.1")
                .site(findSitePort.findById(siteId))
                .build();

        return saveGatewayPort.save(gateway);
    }
}
