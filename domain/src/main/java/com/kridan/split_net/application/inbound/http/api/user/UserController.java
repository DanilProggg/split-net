package com.kridan.split_net.application.inbound.http.api.user;

import com.kridan.split_net.application.inbound.http.api.user.dto.GroupDto;
import com.kridan.split_net.application.inbound.http.api.user.dto.UserDto;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindAllGroupPort;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final FindUserPort findUserPort;
    private final FindAllGroupPort findAllGroupPort;

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        try {

            List<UserDto> usersDto = findUserPort.findAll().stream()
                    .map(
                            user -> {
                                return new UserDto(
                                        user.getUserId().toString(),
                                        user.getEmail(),
                                        user.getReauthIntervalHours(),
                                        user.isRequiredLogin(),
                                        user.getLastLogIn(),
                                        findAllGroupPort.findAllByUser(user.getUserId().toString()).stream()
                                            .map(group -> {
                                                return new GroupDto(
                                                        group.getGroupId().toString(),
                                                        group.getName(),
                                                        group.getDescription()
                                                );
                                            })
                                            .toList()
                                );
                            }
                    ).toList();


            return ResponseEntity.ok(usersDto);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        try {

            User user = findUserPort.findById(UUID.fromString(userId));
            List<Group> groups = findAllGroupPort.findAllByUser(userId);

            UserDto userDto = new UserDto(
                    user.getUserId().toString(),
                    user.getEmail(),
                    user.getReauthIntervalHours(),
                    user.isRequiredLogin(),
                    user.getLastLogIn(),
                    groups.stream()
                            .map(group -> {
                                return new GroupDto(
                                        group.getGroupId().toString(),
                                        group.getName(),
                                        group.getDescription()
                                );
                            })
                            .toList()

            );


            return ResponseEntity.ok(userDto);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
