package com.kridan.split_net.application.inbound.http.api.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ResourceSiteDto {
    private Long siteId;
    private String name;
}
