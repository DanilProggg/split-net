package com.kridan.split_net.domain.resource.services;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.SaveResourcePort;
import com.kridan.split_net.domain.resource.usecases.CreateResourceUseCase;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResourceService implements CreateResourceUseCase {

    private final SaveResourcePort saveResourcePort;
    private final FindGroupPort findGroupPort;

    @Override
    public Resource create(String destination, Long group_id) {

        Resource resource = Resource.builder()
                .destination(destination)
                .group(findGroupPort.findById(group_id))
                .build();

        return resource;

    }
}
