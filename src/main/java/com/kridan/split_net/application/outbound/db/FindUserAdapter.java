package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserAdapter implements FindUserPort {

    private final UserRepository userRepository;

    public FindUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(UUID uuid) {
        return userRepository.getReferenceById(uuid);
    }
}
