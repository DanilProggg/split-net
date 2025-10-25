package com.kridan.split_net.domain.group.usecases;

import com.kridan.split_net.domain.group.Group;

public interface CreateGroupUseCase {
    Group create(String name, String description);
}
