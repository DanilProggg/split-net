package com.kridan.split_net.domain.group.ports;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.infrastructure.database.repository.group.GroupRepository;

public interface FindGroupPort {
    Group findById(Long group_id);
    Group findByName(String name);
}
