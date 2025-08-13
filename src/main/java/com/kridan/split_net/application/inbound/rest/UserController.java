package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.domain.command.CreateUserCommand;
import com.kridan.split_net.domain.ports.inbound.CreateUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateUserCommand command) {
        try {
            log.debug("Обращение к endpoint 'singup'");
            createUserUseCase.createUser(command);

            return ResponseEntity.ok("Пользователь создан");
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }
}
