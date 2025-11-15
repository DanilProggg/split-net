package com.kridan.split_net.application.outbound.db.policy;

import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.policy.ports.FindAllPolicyPort;
import com.kridan.split_net.domain.policy.ports.FindPolicyPort;
import com.kridan.split_net.infrastructure.database.repository.policy.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindPolicyAdapter implements FindAllPolicyPort, FindPolicyPort {

    private final PolicyRepository policyRepository;

    @Override
    public List<Policy> findAll() {
        return policyRepository.findAll();
    }

    @Override
    public Policy findById(String policyId) {
        return policyRepository.findById(UUID.fromString(policyId))
                .orElseThrow(()->new RuntimeException("Policy not found by given ID"));
    }
}
