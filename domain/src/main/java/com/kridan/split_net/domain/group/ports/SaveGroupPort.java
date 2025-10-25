package com.kridan.split_net.domain.group.ports;

import com.kridan.split_net.domain.group.Group;

public interface SaveGroupPort {
    Group save(Group group);
}
