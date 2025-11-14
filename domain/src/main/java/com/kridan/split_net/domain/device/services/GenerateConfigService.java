package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.usecases.GenerateConfigUseCase;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateConfigService implements GenerateConfigUseCase {

    private final FindAllGatewaysPort findAllGatewaysPort;
    private final FindUserPort findUserPort;

    @Override
    public List<Map<String, String>> generate(String user_id, String device_id) {

        User user = findUserPort.findById(UUID.fromString(user_id));
        Set<Group> groups = user.getGroups();


        String allowedIps = groups.stream()
                .flatMap(group -> group.getResources().stream())
                .map(Resource::getDestination)
                .collect(Collectors.joining(","));


        List<Map<String, String>> listOfMaps = findAllGatewaysPort.findAll().stream()
                .map(gateway -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", gateway.getId().toString());
                    map.put("pubkey", gateway.getPublicKey());
                    map.put("allowed_ip", allowedIps);
                    map.put("url", gateway.getWgUrl());
                    return  map;
                }).toList();

        return listOfMaps;
    }
}
