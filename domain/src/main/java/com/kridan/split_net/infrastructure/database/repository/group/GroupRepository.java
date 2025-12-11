package com.kridan.split_net.infrastructure.database.repository.group;

import com.kridan.split_net.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findByName(String name);
    @Query("SELECT g FROM Group g JOIN g.users u WHERE u.userId = :userId")
    List<Group> findAllByUserId(UUID userId);



}
