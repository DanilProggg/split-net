package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.site.Site;

public interface CreateGatewayUseCase {
    Gateway create(String name, String ipAddress, Long site_id);
}
