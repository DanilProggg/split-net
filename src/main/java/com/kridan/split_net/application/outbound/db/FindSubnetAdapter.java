package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.Subnet;
import com.kridan.split_net.domain.ports.outbound.db.FindSubnetPort;
import com.kridan.split_net.infrastructure.database.repository.SubnetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSubnetAdapter implements FindSubnetPort {
    private final SubnetRepository subnetRepository;

    @Override
    public Subnet findByName(String name) {
        return subnetRepository.findByName(name).orElseThrow(
                ()->new RuntimeException("User not found")
        );
    }

    @Override
    public List<Subnet> findAll() {
        return subnetRepository.findAll();
    }
}
