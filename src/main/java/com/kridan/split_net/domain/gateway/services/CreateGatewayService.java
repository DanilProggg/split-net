package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.CreateGatewayUseCase;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGatewayService implements CreateGatewayUseCase {

    private final SaveGatewayPort saveGatewayPort;
    private final FindSitePort findSitePort;


    @Override
    public String create(String name, Long site_id) {


        Gateway gateway = new Gateway(
                findSitePort.findById(site_id),
                name
        );

        saveGatewayPort.save(gateway);

        return "token";
    }
}
