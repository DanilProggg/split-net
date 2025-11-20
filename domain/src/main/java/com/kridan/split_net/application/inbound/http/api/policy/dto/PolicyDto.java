package com.kridan.split_net.application.inbound.http.api.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PolicyDto {
    private String policyId;
    private PolicyResourceDto resource;
    private PolicyGroupDto group;
    private String description;
}
