package com.kridan.split_net.domain.user.services;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.UserRole;
import com.kridan.split_net.domain.user.ports.SaveUserPort;
import com.kridan.split_net.domain.user.usecases.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserService implements CreateUserUseCase {

    private final SaveUserPort saveUserPort;

    @Override
    public User createUser(String userEmail, String userPassword) {
        try {
            log.debug("Создание пользователя {}", userEmail);

            User user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail(userEmail);
            user.setUserRoles(Set.of(UserRole.USER));

            User createdUser = saveUserPort.save(user, userPassword);

            return createdUser;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
