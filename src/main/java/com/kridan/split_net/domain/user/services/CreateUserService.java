package com.kridan.split_net.domain.user.services;

import com.kridan.split_net.domain.user.command.CreateUserCommand;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.usecases.CreateUserUseCase;
import com.kridan.split_net.domain.user.ports.SaveUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
