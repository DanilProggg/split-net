package com.kridan.split_net.domain.command;

import lombok.Getter;

@Getter
public class CreateUserCommand {
    private String username;
    private String password;
}
