package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateUserCommand;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateUserUseCase;
import com.kridan.split_net.domain.ports.outbound.db.SaveUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserService implements CreateUserUseCase {

    private final SaveUserPort saveUserPort;

    @Override
    public User createUser(CreateUserCommand command) {
        try {
            log.debug("Создание пользователя {}", command.getUsername());

            User user = new User()
                    .setId(UUID.randomUUID())
                    .setUsername(command.getUsername())
                    .setPassword(command.getPassword());

            User createdUser = saveUserPort.save(user);

            User realUser = (User) Hibernate.unproxy(createdUser);
            log.debug("Пользователь создан {}", realUser.toString());

            return createdUser;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
