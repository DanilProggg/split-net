package com.kridan.split_net.domain.group.ports;

import com.kridan.split_net.domain.group.Group;

import java.util.List;

public interface FindAllGroupPort {
    List<Group> findAll();
}
