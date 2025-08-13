package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.command.CreateUserCommand;

public interface CreateUserUseCase {
    void createUser(CreateUserCommand command);
}
