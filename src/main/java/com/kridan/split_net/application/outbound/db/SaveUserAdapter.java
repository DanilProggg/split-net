package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.SaveUserPort;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveUserAdapter implements SaveUserPort {

    private final UserRepository userRepository;

    public SaveUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.getReferenceById(user.getId());
    }
}
