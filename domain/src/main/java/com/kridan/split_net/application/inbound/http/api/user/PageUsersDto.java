package com.kridan.split_net.application.inbound.http.api.user;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PageUsersDto {
    private String email;
    private int page;
}
