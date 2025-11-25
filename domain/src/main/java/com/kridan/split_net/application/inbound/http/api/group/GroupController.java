package com.kridan.split_net.application.inbound.http.api.group;

import com.kridan.split_net.application.inbound.http.api.dto.CreateGroupRequest;
import com.kridan.split_net.application.inbound.http.api.group.dto.GroupDto;
import com.kridan.split_net.application.inbound.http.api.group.dto.PolicyDto;
import com.kridan.split_net.application.inbound.http.api.group.dto.UserDto;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.usecases.AddUserToGroupUseCase;
import com.kridan.split_net.domain.group.usecases.CreateGroupUseCase;
import com.kridan.split_net.domain.group.usecases.GetAllGroupUseCase;
import com.kridan.split_net.domain.resource.usecases.CreateResourceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final GetAllGroupUseCase getAllGroupUseCase;
    private final AddUserToGroupUseCase addUserToGroupUseCase;
    private final CreateResourceUseCase createResourceUseCase;

    @PostMapping()
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest createGroupRequest){
        try {

            String desc = createGroupRequest.getDescription();
            Group group = createGroupUseCase.create(
                    createGroupRequest.getName(),
                    (desc == null || desc.isBlank()) ? null : desc

            );

            return ResponseEntity.ok("Group created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/{group_id}/users/{user_id}")
    public ResponseEntity<?> addUserToGroup(@PathVariable("group_id") Long group_id, @PathVariable("user_id") String user_id){
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

            List<GroupDto> groupDtos = groups.stream()
                    .map(group -> new GroupDto(
                            group.getGroupId(),
                            group.getName(),
                            group.getDescription(),
                            group.getPolicies().stream()
                                    .map(r -> new PolicyDto(r.getPolicyId().toString(), r.getResource().getDestination()))
                                    .collect(Collectors.toSet()),
                            group.getUsers().stream()
                                    .map(u -> new UserDto(u.getUserId().toString(), u.getEmail()))
                                    .collect(Collectors.toSet())
                    )).toList();

            return ResponseEntity.ok(groupDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}


