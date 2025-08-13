package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateUserCommand;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateUserUseCase;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(CreateUserCommand command) {
        try {
            log.debug(String.format("Создание пользователя %s", command.getUsername()));

            User user = new User()
                    .setId(UUID.randomUUID())
                    .setLogin(command.getUsername())
                    .setPassword(command.getPassword());

            userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
