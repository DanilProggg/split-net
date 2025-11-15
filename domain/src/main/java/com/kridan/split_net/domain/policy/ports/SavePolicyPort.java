package com.kridan.split_net.domain.policy.ports;

import com.kridan.split_net.domain.policy.Policy;

public interface SavePolicyPort {
    Policy save(Policy policy);
}
