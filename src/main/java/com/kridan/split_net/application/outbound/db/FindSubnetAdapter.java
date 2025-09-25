package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.ports.FindSubnetPort;
import com.kridan.split_net.infrastructure.database.repository.subnet.SubnetRepository;
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

    @Override
    public Subnet findById(Long id) {
        return subnetRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Subnet not found")
        );
    }
}
