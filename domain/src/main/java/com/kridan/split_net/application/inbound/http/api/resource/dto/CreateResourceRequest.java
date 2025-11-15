package com.kridan.split_net.application.inbound.http.api.resource.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateResourceRequest {
    private String destination; //CIDR or IP
    private Long site_id;
}
