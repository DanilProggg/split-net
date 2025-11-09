package com.kridan.split_net.application.inbound.http_api.user;

import com.kridan.split_net.application.inbound.http_api.dto.JwtResponse;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.usecases.CreateUserUseCase;
import com.kridan.split_net.infrastructure.security.JpaUserDetailsService;
import com.kridan.split_net.infrastructure.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CreateUserUseCase createUserUseCase;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            log.debug("Call endpoint 'singup'");

            User user = createUserUseCase.createUser(userDto.getEmail(), userDto.getPassword());

            log.debug("User created. UUID: {}", user.getId().toString());
            return ResponseEntity.ok("User created.");
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            jpaUserDetailsService.auth(userDto.getEmail(), userDto.getPassword());

            String token = jwtUtils.generateUserToken(userDto.getEmail());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
