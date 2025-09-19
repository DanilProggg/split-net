package com.kridan.split_net.infrastructure.database.repository;

import com.kridan.split_net.domain.model.Subnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubnetRepository extends JpaRepository<Subnet, Long> {
    Optional<Subnet> findByName(String name);
}
