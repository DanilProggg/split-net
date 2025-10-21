package com.kridan.split_net.domain.group.services;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindAllGroupPort;
import com.kridan.split_net.domain.group.usecases.GetAllGroupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllGroupService implements GetAllGroupUseCase {

    private final FindAllGroupPort findAllGroupPort;

    @Override
    public List<Group> getAll() {
        return findAllGroupPort.findAll();
    }
}
