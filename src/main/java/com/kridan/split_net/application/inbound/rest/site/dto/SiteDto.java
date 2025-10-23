package com.kridan.split_net.application.inbound.rest.site.dto;

import com.kridan.split_net.application.inbound.rest.gateway.dto.GatewayDto;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SiteDto {
    private Long id;
    private String name;
    private String description;
    private String subnet;
    private List<GatewayDto> gatewayDtos;

    public SiteDto(Long id, String name, String description, String subnet, List<GatewayDto> gatewayDtos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subnet = subnet;
        this.gatewayDtos = gatewayDtos;
    }
}
