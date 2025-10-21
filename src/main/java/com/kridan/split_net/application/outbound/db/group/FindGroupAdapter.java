package com.kridan.split_net.application.outbound.db.group;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindAllGroupPort;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.infrastructure.database.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindGroupAdapter implements FindGroupPort, FindAllGroupPort {

    private final GroupRepository groupRepository;

    @Override
    public Group findByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(()->new RuntimeException("Group not found by given name"));
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
