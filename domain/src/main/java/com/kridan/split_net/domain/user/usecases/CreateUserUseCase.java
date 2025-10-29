package com.kridan.split_net.domain.user.usecases;

import com.kridan.split_net.domain.user.command.CreateUserCommand;
import com.kridan.split_net.domain.user.User;

public interface CreateUserUseCase {
    User createUser(String userEmail, String userPassword);
}
