package com.kridan.split_net.domain.resource.services;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.FindAllResourcesPort;
import com.kridan.split_net.domain.resource.usecases.GetAllResourcesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllResourcesService implements GetAllResourcesUseCase {

    private final FindAllResourcesPort findAllResourcesPort;

    @Override
    public List<Resource> getAll() {
        return findAllResourcesPort.findAll();
    }
}
