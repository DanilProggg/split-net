package com.kridan.split_net.application.inbound.http.api.user;

import com.kridan.split_net.application.inbound.http.api.user.dto.UserDto;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final FindUserPort findUserPort;

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        try {

            List<UserDto> usersDto = findUserPort.findAll().stream()
                    .map(
                            user -> {
                                return new UserDto(
                                  user.getId().toString(),
                                  user.getEmail(),
                                  user.getReauthIntervalHours(),
                                  user.isRequiredLogin(),
                                  user.getLastLogIn()
                                );
                            }
                    ).toList();


            return ResponseEntity.ok(usersDto);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
