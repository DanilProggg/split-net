package com.kridan.split_net.domain.policy.usecases;

import com.kridan.split_net.domain.policy.Policy;

public interface CreatePolicyUseCase {
    Policy create(Long resourceId, Long groupId, String description);
}
