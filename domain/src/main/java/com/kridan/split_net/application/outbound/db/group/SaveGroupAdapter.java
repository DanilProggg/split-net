package com.kridan.split_net.application.outbound.db.group;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.SaveGroupPort;
import com.kridan.split_net.infrastructure.database.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveGroupAdapter implements SaveGroupPort {

    private final GroupRepository groupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}
