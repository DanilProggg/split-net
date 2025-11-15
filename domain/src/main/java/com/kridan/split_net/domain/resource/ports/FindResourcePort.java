package com.kridan.split_net.domain.resource.ports;

import com.kridan.split_net.domain.resource.Resource;

public interface FindResourcePort {
    Resource findById(Long id);
}
