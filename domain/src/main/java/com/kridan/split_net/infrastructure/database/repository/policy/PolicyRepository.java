package com.kridan.split_net.infrastructure.database.repository.policy;

import com.kridan.split_net.domain.policy.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, UUID> {
}
