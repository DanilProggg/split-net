package com.kridan.split_net.domain.user.command;

import lombok.Getter;

@Getter
public class CreateUserCommand {
    private String username;
    private String password;
}
