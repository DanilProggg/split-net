package com.kridan.split_net.domain.policy.ports;

import com.kridan.split_net.domain.policy.Policy;

import java.util.List;

public interface FindAllPolicyPort {
    List<Policy> findAll();
}
