package com.kridan.split_net.domain.policy.services;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.group.ports.FindGroupPort;
import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.policy.ports.SavePolicyPort;
import com.kridan.split_net.domain.policy.usecases.CreatePolicyUseCase;
import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.FindResourcePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePolicyService implements CreatePolicyUseCase {

    private final SavePolicyPort savePolicyPort;
    private final FindGroupPort findGroupPort;
    private final FindResourcePort findResourcePort;

    @Override
    public Policy create(Long resourceId, Long groupId, String description) {
        Group group = findGroupPort.findById(groupId);
        Resource resource = findResourcePort.findById(resourceId);

        Policy policy = Policy.builder()
                .policyId(UUID.randomUUID())
                .resource(resource)
                .group(group)
                .description(description)
                .build();

        return savePolicyPort.save(policy);
    }
}
