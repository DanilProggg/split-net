package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.ports.FindDevicePort;
import com.kridan.split_net.domain.device.usecases.GenerateConfigUseCase;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateConfigService implements GenerateConfigUseCase {

    private final FindAllGatewaysPort findAllGatewaysPort;

    @Override
    public List<Map<String, String>> generate(String device_id) {

        List<Map<String, String>> listOfMaps = findAllGatewaysPort.findAll().stream()
                .map(gateway -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("allowed_id", gateway.getId().toString());
                    map.put("pubkey", gateway.getPublicKey());
                    map.put("ip", gateway.getSite().getSubnet());
                    map.put("url", gateway.getWgUrl());
                    return  map;
                }).toList();

        return listOfMaps;
    }
}
