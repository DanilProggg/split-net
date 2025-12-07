package com.kridan.split_net.domain.group.ports;

import com.kridan.split_net.domain.group.Group;

public interface FindGroupPort {
    Group findById(String group_id);
    Group findByName(String name);
}
