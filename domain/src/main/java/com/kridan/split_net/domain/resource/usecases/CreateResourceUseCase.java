package com.kridan.split_net.domain.resource.usecases;

import com.kridan.split_net.domain.resource.Resource;

public interface CreateResourceUseCase {
    Resource create(String destination, Long group_id);
}
