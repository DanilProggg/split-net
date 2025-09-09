package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.User;

public interface SaveUserPort {
    User save(String email, String password);
}
