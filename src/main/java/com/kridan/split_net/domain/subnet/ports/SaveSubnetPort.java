package com.kridan.split_net.domain.subnet.ports;

import com.kridan.split_net.domain.subnet.Subnet;

public interface SaveSubnetPort {
    Subnet save(Subnet subnet);
}
