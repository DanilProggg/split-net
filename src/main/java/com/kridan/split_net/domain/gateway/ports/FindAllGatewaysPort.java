package com.kridan.split_net.domain.gateway.ports;

import com.kridan.split_net.domain.gateway.Gateway;

import java.util.List;

public interface FindAllGatewaysPort {
    List<Gateway> findAll();
}
