package com.kridan.split_net.domain.user.ports;

import com.kridan.split_net.domain.user.User;

public interface SaveUserPort {
    User save(User savedUser, String password);
    User save(User user);
}
