package com.kridan.split_net.application.inbound.http.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class GroupDto {
    private String groupId;
    private String name;
    private String description;
}
