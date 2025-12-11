package com.kridan.split_net.application.outbound.db.resource;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.FindAllResourcesPort;
import com.kridan.split_net.domain.resource.ports.FindResourcePort;
import com.kridan.split_net.infrastructure.database.repository.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindResourceAdapter implements FindAllResourcesPort, FindResourcePort {

    private final ResourceRepository resourceRepository;

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource findById(String resourceId) {
        return  resourceRepository.findById(UUID.fromString(resourceId))
                .orElseThrow(()->new RuntimeException("Can not find resource with id: " + String.valueOf(resourceId)));
    }
}
