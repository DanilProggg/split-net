package com.kridan.split_net.domain.subnet.usecases;

import com.kridan.split_net.domain.subnet.Subnet;

public interface CreateSubnetUseCase {
    Subnet create(String name, String description, String cidr);
}
