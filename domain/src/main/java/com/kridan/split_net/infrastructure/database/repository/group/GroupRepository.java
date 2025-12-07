package com.kridan.split_net.infrastructure.database.repository.group;

import com.kridan.split_net.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findByName(String name);
}
