package com.kridan.split_net.infrastructure.database.repository.device;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    public List<Device> findAllByOwner(User user);
    public Optional<Device> findByOwnerAndDeviceId(User user, UUID deviceId);
}
