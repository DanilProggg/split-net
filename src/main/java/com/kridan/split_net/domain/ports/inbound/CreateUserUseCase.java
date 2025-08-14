package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.command.CreateUserCommand;
import com.kridan.split_net.domain.model.User;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
}
