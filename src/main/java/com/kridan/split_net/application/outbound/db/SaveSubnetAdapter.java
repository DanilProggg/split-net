package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.Subnet;
import com.kridan.split_net.domain.ports.outbound.db.SaveSubnetPort;
import com.kridan.split_net.infrastructure.database.repository.SubnetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveSubnetAdapter implements SaveSubnetPort {
    private final SubnetRepository subnetRepository;
    @Override
    public Subnet save(Subnet subnet) {
        return subnetRepository.save(subnet);
    }
}
