package com.kridan.split_net.application.inbound.http.api.group;

import com.kridan.split_net.application.inbound.http.api.dto.CreateGroupRequest;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.group.usecases.AddUserToGroupUseCase;
import com.kridan.split_net.domain.group.usecases.CreateGroupUseCase;
import com.kridan.split_net.domain.group.usecases.GetAllGroupUseCase;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final GetAllGroupUseCase getAllGroupUseCase;
    private final AddUserToGroupUseCase addUserToGroupUseCase;

    @PostMapping()
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest createGroupRequest){
        try {
            Group group = createGroupUseCase.create(
                    createGroupRequest.getName(),
                    createGroupRequest.getDescription()
            );

            return ResponseEntity.ok("Group created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/{group_id}/users/{user_id}")
    public ResponseEntity<?> addUserToGroup(@RequestParam("group_id") Long group_id, @RequestParam("user_id") String user_id){
        try {
            Group group = addUserToGroupUseCase.add(group_id, user_id);

            return ResponseEntity.ok(group);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }


    @GetMapping()
    public ResponseEntity<?> getGroups() {
        try {
            List<Group> groups = getAllGroupUseCase.getAll();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}


