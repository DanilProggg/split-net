package com.kridan.split_net.domain.subnet.services;

import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.usecases.CreateSubnetUseCase;
import com.kridan.split_net.domain.subnet.ports.SaveSubnetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSubnetService implements CreateSubnetUseCase {

    private final SaveSubnetPort saveSubnetPort;

    @Override
    public Subnet create(String name, String description, String cidr) {
        Subnet subnet = new Subnet()
                .setName(name)
                .setDescription(description)
                .setCidr(cidr);
        return saveSubnetPort.save(subnet);
    }
}
