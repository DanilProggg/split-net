package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.ports.FindAllDevicesPort;
import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.GatewayConfig.GatewayConfig;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.ports.SaveGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.GenerateConfigUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerateConfigService implements GenerateConfigUseCase {

    private final FindGatewayPort findGatewayPort;
    private final SaveGatewayPort saveGatewayPort;
    private final FindAllGatewaysPort findAllGatewaysPort;
    private final FindAllDevicesPort findAllDevicesPort;

    @Override
    public GatewayConfig generate(Long gateway_id) {

        Gateway gw = findGatewayPort.findById(gateway_id);

        Map<String, String> peers = new HashMap<>();

        List<Gateway> gatewayListExcludeSelected = findAllGatewaysPort.findAll()
                .stream()
                .filter(gateway -> !gateway.getId().equals(gateway_id))
                .toList();

        for (Gateway gw_i : gatewayListExcludeSelected){
            peers.put(gw_i.getPublicKey(), gw_i.getIpAddress());
        }

        List<Device> deviceList = findAllDevicesPort.findAll();

        for (Device d : deviceList){
            peers.put(d.getPublicKey(), d.getIpAddress());
        }

        GatewayConfig gatewayConfig =  new GatewayConfig();
        gatewayConfig.setPeers(peers);

        return gatewayConfig;
    }
}
