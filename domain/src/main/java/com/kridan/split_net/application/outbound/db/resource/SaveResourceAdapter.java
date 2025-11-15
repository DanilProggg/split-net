package com.kridan.split_net.application.outbound.db.resource;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.SaveResourcePort;
import com.kridan.split_net.infrastructure.database.repository.resource.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveResourceAdapter implements SaveResourcePort {

    private final ResourceRepository resourceRepository;

    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }
}
