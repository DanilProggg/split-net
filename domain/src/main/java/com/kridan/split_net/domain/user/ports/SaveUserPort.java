package com.kridan.split_net.domain.user.ports;

import com.kridan.split_net.domain.user.User;

public interface SaveUserPort {
    User save(String email, String password);
}
