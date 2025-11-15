package com.kridan.split_net.application.inbound.http.api.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PolicyDto {
    private String policyId;
    private String destination;
}
