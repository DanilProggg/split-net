package com.kridan.split_net.domain.group.services;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.SaveGroupPort;
import com.kridan.split_net.domain.group.usecases.CreateGroupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGroupService implements CreateGroupUseCase {

    private final SaveGroupPort saveGroupPort;

    @Override
    public Group create(String name, String description) {

        Group group = new Group(
                name,
                description
        );

        return saveGroupPort.save(group);
    }
}
