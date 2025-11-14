package com.kridan.split_net.domain.group.services;


import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.group.ports.SaveGroupPort;
import com.kridan.split_net.domain.group.usecases.AddUserToGroupUseCase;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddUserToGroupService implements AddUserToGroupUseCase {

    private final FindUserPort findUserPort;
    private final FindGroupPort findGroupPort;
    private final SaveGroupPort saveGroupPort;

    @Override
    public Group add(Long group_id, String user_id) {
        Group group = findGroupPort.findById(group_id);
        User user = findUserPort.findById(UUID.fromString(user_id));

        Set<User> users = group.getUsers();
        users.add(user);

        group.setUsers(users);
        return saveGroupPort.save(group);

    }
}
