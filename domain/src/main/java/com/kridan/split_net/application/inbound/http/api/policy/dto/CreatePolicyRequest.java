package com.kridan.split_net.application.inbound.http.api.policy.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreatePolicyRequest {
    private Long resourceId;
    private Long groupId;
    private String description;
}
