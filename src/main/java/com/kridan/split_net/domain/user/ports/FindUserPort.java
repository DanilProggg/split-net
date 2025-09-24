package com.kridan.split_net.domain.user.ports;

import com.kridan.split_net.domain.user.User;

import java.util.UUID;

public interface FindUserPort {
    User findById(UUID uuid);
    User findByEmail(String email);
}
