package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.User;

import java.util.UUID;

public interface FindUserPort {
    User findById(UUID uuid);
}
