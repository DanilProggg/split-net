package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.application.inbound.rest.dto.JwtResponse;
import com.kridan.split_net.application.inbound.rest.dto.LoginUserDto;
import com.kridan.split_net.infrastructure.security.JpaUserDetailsService;
import com.kridan.split_net.infrastructure.security.JwtUtils;
import com.kridan.split_net.domain.user.command.CreateUserCommand;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.usecases.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateUserCommand command) {
        try {
            log.debug("Call endpoint 'singup'");

            User user = createUserUseCase.createUser(command);

            log.debug("User created. UUID: {}", user.getId().toString());
            return ResponseEntity.ok("User created.");
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto) {
        try {
            log.debug("Call endpoint 'signin'");
            jpaUserDetailsService.auth(loginUserDto.getEmail(), loginUserDto.getPassword());
            // Auth

            log.debug("User found");

            // Generate JWT

            String token = jwtUtils.generateUserToken(loginUserDto.getEmail());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
