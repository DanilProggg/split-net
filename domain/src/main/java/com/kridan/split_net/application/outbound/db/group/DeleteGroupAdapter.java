package com.kridan.split_net.application.outbound.db.group;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.DeleteGroupPort;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.infrastructure.database.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteGroupAdapter implements DeleteGroupPort {
    private final FindGroupPort findGroupPort;
    private final GroupRepository groupRepository;

    @Override
    public boolean delete(String groupId) {
        try {
            Group group = findGroupPort.findById(groupId);
            groupRepository.delete(group);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
