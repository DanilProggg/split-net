package com.kridan.split_net.infrastructure.database.repository.user;

import com.kridan.split_net.domain.user.LocalCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalCredentialsRepository extends JpaRepository<LocalCredentials, Long> {
}
