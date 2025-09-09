package com.kridan.split_net.infrastructure.database.repository;

import com.kridan.split_net.domain.model.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
}
