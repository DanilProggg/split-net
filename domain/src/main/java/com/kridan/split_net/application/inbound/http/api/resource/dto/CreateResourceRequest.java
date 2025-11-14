package com.kridan.split_net.application.inbound.http.api.resource.dto;

import com.kridan.split_net.domain.group.Group;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateResourceRequest {
    private String destination; //CIDR or IP
}
