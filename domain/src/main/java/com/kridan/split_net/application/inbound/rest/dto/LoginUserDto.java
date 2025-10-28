package com.kridan.split_net.application.inbound.rest.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
