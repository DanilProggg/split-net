package com.kridan.split_net.application.inbound.http_api.user;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
}
