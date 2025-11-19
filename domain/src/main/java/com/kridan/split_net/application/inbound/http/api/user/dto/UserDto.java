package com.kridan.split_net.application.inbound.http.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
@AllArgsConstructor
public class UserDto {
    private String user_id;
    private String email;
    private int reauthIntervalHours;
    private boolean requiredLogin;
    private Date lastLogIn;

}
