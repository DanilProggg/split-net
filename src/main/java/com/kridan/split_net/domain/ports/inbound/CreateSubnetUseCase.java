package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.model.Subnet;
import com.kridan.split_net.infrastructure.database.repository.SubnetRepository;

public interface CreateSubnetUseCase {
    Subnet create(String name, String description, String cidr);
}
