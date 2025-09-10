package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserAdapter implements FindUserPort {

    private final UserRepository userRepository;


    @Override
    public User findById(UUID uuid) {
        return userRepository.getReferenceById(uuid);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User not found")
        );
    }
}
