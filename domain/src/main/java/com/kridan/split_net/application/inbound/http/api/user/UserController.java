package com.kridan.split_net.application.inbound.http.api.user;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final FindUserPort findUserPort;

    @GetMapping("/page")
    public ResponseEntity<?> getUsers(@RequestBody PageUsersDto pageUsersDto) {
        try {

            Page<User> page = findUserPort.searchByEmail(pageUsersDto.getEmail(), pageUsersDto.getPage());

            return ResponseEntity.ok(page);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
