package com.kridan.split_net.domain.resource.ports;

import com.kridan.split_net.domain.resource.Resource;

import java.util.List;

public interface FindAllResourcesPort {
    List<Resource> findAll();
}
