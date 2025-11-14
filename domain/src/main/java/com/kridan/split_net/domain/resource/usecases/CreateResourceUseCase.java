package com.kridan.split_net.domain.resource.usecases;

import com.kridan.split_net.domain.resource.Resource;

public interface CreateResourceUseCase {
    Resource save(String destination, Long group_id);
}
