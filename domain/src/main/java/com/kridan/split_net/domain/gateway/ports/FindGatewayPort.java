package com.kridan.split_net.domain.gateway.ports;

import com.kridan.split_net.domain.gateway.Gateway;

public interface FindGatewayPort {
    Gateway findById(String id);
    Gateway findByName(String name);
}
