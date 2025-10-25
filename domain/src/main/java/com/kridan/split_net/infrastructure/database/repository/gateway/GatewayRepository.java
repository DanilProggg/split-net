package com.kridan.split_net.infrastructure.database.repository.gateway;

import com.kridan.split_net.domain.gateway.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {

    Optional<Gateway> findByName(String name);
}
