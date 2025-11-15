package com.kridan.split_net.application.inbound.http.api.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
@Getter
@AllArgsConstructor
public class GroupDto {

    private Long id;
    private String name;
    private String description;
    private Set<PolicyDto> policies;
    private Set<UserDto> users;
}
