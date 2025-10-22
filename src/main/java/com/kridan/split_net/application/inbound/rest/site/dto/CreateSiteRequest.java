package com.kridan.split_net.application.inbound.rest.site.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateSiteRequest {
    private String name;
    private String description;
    private String subnet;

}
