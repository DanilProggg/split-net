package com.kridan.split_net.application.inbound.http.api.site.dto;

import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SiteDto {
    private Long site_id;
    private String name;
    private String description;
    private String subnet;
    private List<GatewayDto> gateways;

    public SiteDto(Long site_id, String name, String description, String subnet, List<GatewayDto> gatewayDtos) {
        this.site_id = site_id;
        this.name = name;
        this.description = description;
        this.subnet = subnet;
        this.gateways = gatewayDtos;
    }
}
