package com.kridan.split_net.domain.gateway.usecases;

import com.kridan.split_net.domain.site.Site;

public interface CreateGatewayUseCase {
    String create(String name, Long site_id);
}
