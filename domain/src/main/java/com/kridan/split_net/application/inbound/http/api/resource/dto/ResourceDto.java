package com.kridan.split_net.application.inbound.http.api.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ResourceDto {
    private Long resourceId;
    private String destination;
    private Long siteId;
}
