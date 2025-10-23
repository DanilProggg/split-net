package com.kridan.split_net.application.inbound.rest.gateway.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GatewayDto {
    private Long gateway_id;
    private String name;
    private String wg_url;
    private Long site_id;

    public GatewayDto(Long gateway_id, String name, String wg_url, Long site_id) {
        this.gateway_id = gateway_id;
        this.name = name;
        this.wg_url = wg_url;
        this.site_id = site_id;
    }
}
