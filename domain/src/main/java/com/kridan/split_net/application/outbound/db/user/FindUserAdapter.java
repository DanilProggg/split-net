package com.kridan.split_net.application.outbound.db.user;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.infrastructure.database.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<User> searchByEmail(String emailPart, int page) {
        Pageable pageable = PageRequest.of(page, 50);
        return userRepository.searchByEmail(emailPart, pageable);
    }
}
