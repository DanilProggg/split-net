package com.kridan.split_net.application.inbound.http.api.gateway.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class GatewayDto {
    private Long gateway_id;
    private String name;
    private String wg_url;
    private String publicKey;
    private String ipAddress;
    private boolean isActive;
    private Long site_id;

    public GatewayDto(Long gateway_id, String name, String wg_url, String publicKey, String ipAddress, Date lastSeen, Long site_id) {
        this.gateway_id = gateway_id;
        this.name = name;
        this.wg_url = wg_url;
        this.publicKey = publicKey;
        this.ipAddress = ipAddress;
        this.isActive = (System.currentTimeMillis() - lastSeen.getTime()) < 2 * 60 * 1000;
        this.site_id = site_id;
    }
}
