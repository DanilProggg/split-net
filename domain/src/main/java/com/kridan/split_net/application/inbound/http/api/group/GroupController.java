package com.kridan.split_net.application.inbound.http.api.group;

import com.kridan.split_net.application.inbound.http.api.dto.CreateGroupRequest;
import com.kridan.split_net.application.inbound.http.api.group.dto.GroupDto;
import com.kridan.split_net.application.inbound.http.api.group.dto.ResourceDto;
import com.kridan.split_net.application.inbound.http.api.group.dto.UserDto;
import com.kridan.split_net.application.inbound.http.api.resource.dto.CreateResourceRequest;
import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.group.usecases.AddUserToGroupUseCase;
import com.kridan.split_net.domain.group.usecases.CreateGroupUseCase;
import com.kridan.split_net.domain.group.usecases.GetAllGroupUseCase;
import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.usecases.CreateResourceUseCase;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    public ResponseEntity<?> addUserToGroup(@PathVariable("group_id") Long group_id, @PathVariable("user_id") String user_id){
        try {
            Group group = addUserToGroupUseCase.add(group_id, user_id);

            return ResponseEntity.ok(group);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/{group_id}/resources/")
    public ResponseEntity<?> createResource(@RequestBody CreateResourceRequest createResourceRequest, @PathVariable("group_id") Long group_id) {
        try {

            Resource resource = createResourceUseCase.save(
                    createResourceRequest.getDestination(), group_id);

            return ResponseEntity.ok(resource);
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
                            group.getId(),
                            group.getName(),
                            group.getDescription(),
                            group.getResources().stream()
                                    .map(r -> new ResourceDto(r.getId(), r.getDestination()))
                                    .collect(Collectors.toSet()),
                            group.getUsers().stream()
                                    .map(u -> new UserDto(u.getId().toString(), u.getEmail()))
                                    .collect(Collectors.toSet())
                    )).toList();

            return ResponseEntity.ok(groupDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}


