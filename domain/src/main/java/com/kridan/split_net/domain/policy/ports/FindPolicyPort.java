package com.kridan.split_net.domain.policy.ports;

import com.kridan.split_net.domain.policy.Policy;

public interface FindPolicyPort {
    Policy findById(String policyId);
}
