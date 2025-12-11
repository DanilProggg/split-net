package com.kridan.split_net.application.inbound.http.api.resource.dto;

import com.nimbusds.jose.util.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ResourceDto {
    private String resourceId;
    private String destination;
    private ResourceSiteDto site;
}
