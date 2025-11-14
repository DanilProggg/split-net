package com.kridan.split_net.application.inbound.http.api.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
}
