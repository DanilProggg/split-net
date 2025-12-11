package com.kridan.split_net.application.outbound.db.group;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindAllGroupPort;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.infrastructure.database.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindGroupAdapter implements FindGroupPort, FindAllGroupPort {

    private final GroupRepository groupRepository;

    @Override
    public Group findById(String group_id) {
        return groupRepository.findById(UUID.fromString(group_id))
                .orElseThrow(()->new RuntimeException("Group not found by given ID"));
    }

    @Override
    public Group findByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(()->new RuntimeException("Group not found by given name"));
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAllByUser(String userId) {
        return groupRepository.findAllByUserId(UUID.fromString(userId));
    }
}
