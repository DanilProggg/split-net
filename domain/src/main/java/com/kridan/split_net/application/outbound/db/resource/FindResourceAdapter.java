package com.kridan.split_net.application.outbound.db.resource;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.FindAllResourcesPort;
import com.kridan.split_net.infrastructure.database.repository.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindResourceAdapter implements FindAllResourcesPort {

    private final ResourceRepository resourceRepository;

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }
}
