package com.kridan.split_net.domain.resource.usecases;

import com.kridan.split_net.domain.resource.Resource;

import java.util.List;

public interface GetAllResourcesUseCase {
    List<Resource> getAll();
}
