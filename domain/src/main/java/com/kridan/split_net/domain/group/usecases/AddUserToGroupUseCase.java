package com.kridan.split_net.domain.group.usecases;

import com.kridan.split_net.domain.group.Group;

public interface AddUserToGroupUseCase {
    Group add(Long group_id, String user_id);
}
