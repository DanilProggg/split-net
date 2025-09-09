package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.command.CreateUserCommand;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.model.UserIdentity;
import com.kridan.split_net.domain.ports.inbound.CreateUserUseCase;
import com.kridan.split_net.domain.ports.outbound.db.SaveUserPort;
import jakarta.transaction.Transactional;
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
    @Transactional
    public User createUser(CreateUserCommand command) {
        try {
            log.debug("Создание пользователя {}", command.getUsername());

            User createdUser = saveUserPort.save(command.getUsername(), command.getPassword());

            return createdUser;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
