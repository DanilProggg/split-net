package com.kridan.split_net.application.inbound.http.api.user.dto;

import lombok.Data;

@Data
public class AuthUserDto {
    private String email;
    private String password;
}
