package com.kridan.split_net.application.inbound.http.api.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PolicyResourceDto {
    private Long policyId;
    private String destination;
}
