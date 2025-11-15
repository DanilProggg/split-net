package com.kridan.split_net.application.outbound.db.policy;

import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.policy.ports.SavePolicyPort;
import com.kridan.split_net.infrastructure.database.repository.policy.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavePolicyAdapter implements SavePolicyPort {

    private final PolicyRepository policyRepository;

    @Override
    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }
}
