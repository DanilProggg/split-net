package com.kridan.split_net.application.inbound.rest.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class CreateSubnetRequest {
    private String name;
    private String description;
    private String cidr;
}
