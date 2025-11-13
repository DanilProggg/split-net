package com.kridan.split_net.application.inbound.http.api.site.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateSiteRequest {
    private String name;
    private String description;
}
