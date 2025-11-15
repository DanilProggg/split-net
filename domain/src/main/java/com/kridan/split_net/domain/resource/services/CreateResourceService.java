package com.kridan.split_net.domain.resource.services;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.SaveResourcePort;
import com.kridan.split_net.domain.resource.usecases.CreateResourceUseCase;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResourceService implements CreateResourceUseCase {

    private final SaveResourcePort saveResourcePort;
    private final FindSitePort findSitePort;

    @Override
    public Resource create(String destination, Long site_id) {

        Resource resource = Resource.builder()
                .destination(destination)
                .site(findSitePort.findById(site_id))
                .build();

        return saveResourcePort.save(resource);

    }
}
