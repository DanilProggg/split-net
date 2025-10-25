package com.kridan.split_net.application.inbound.rest.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateGroupRequest {
    String name;
    String description;
}
