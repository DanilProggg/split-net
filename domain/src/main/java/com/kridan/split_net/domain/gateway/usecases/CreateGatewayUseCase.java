package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.gateway.Gateway;

public interface CreateGatewayUseCase {
    Gateway create(String gatewayId, String name, String site_id);
}
