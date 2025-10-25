package com.kridan.split_net.infrastructure.database.repository.user;

import com.kridan.split_net.domain.user.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
}
