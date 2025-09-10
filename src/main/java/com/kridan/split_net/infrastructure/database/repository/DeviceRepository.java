package com.kridan.split_net.infrastructure.database.repository;

import com.kridan.split_net.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    public List<Device> findAllByEmail(String email);
}
