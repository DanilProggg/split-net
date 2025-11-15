package com.kridan.split_net.application.inbound.http.api.policy;

import com.kridan.split_net.application.inbound.http.api.policy.dto.CreatePolicyRequest;
import com.kridan.split_net.application.inbound.http.api.resource.dto.CreateResourceRequest;
import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.policy.ports.FindAllPolicyPort;
import com.kridan.split_net.domain.policy.usecases.CreatePolicyUseCase;
import com.kridan.split_net.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/policies")
@Slf4j
@RequiredArgsConstructor
public class PolicyController {

    private final CreatePolicyUseCase createPolicyUseCase;
    private final FindAllPolicyPort findAllPolicyPort;

    @PostMapping()
    public ResponseEntity<?> createPolicy(@RequestBody CreatePolicyRequest createPolicyRequest) {
        try {

            Policy policy = createPolicyUseCase.create(
                    createPolicyRequest.getResourceId(),
                    createPolicyRequest.getGroupId(),
                    createPolicyRequest.getDescription().isBlank() ? null : createPolicyRequest.getDescription()
            );

            return ResponseEntity.ok(policy);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }


    @GetMapping()
    public ResponseEntity<?> getPolicies() {
        try {
            List<Policy> policies = findAllPolicyPort.findAll();

            return ResponseEntity.ok(policies);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
