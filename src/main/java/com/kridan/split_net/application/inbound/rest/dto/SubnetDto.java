package com.kridan.split_net.application.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
public class SubnetDto {
    private String name;
    private String description;
    private String cidr;
    private List<String> devices;
}
