package com.kridan.split_net.application.inbound.http;

import com.kridan.split_net.application.inbound.http.dto.CreateGroupRequest;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.usecases.CreateGroupUseCase;
import com.kridan.split_net.domain.group.usecases.GetAllGroupUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final GetAllGroupUseCase getAllGroupUseCase;

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


