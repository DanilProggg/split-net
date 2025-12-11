package com.kridan.split_net.infrastructure.database.repository.resource;

import com.kridan.split_net.domain.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}
