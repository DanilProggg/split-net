package com.kridan.split_net.domain.subnet.ports;

import com.kridan.split_net.domain.subnet.Subnet;

import java.util.List;

public interface FindSubnetPort {
    Subnet findByName(String name);
    List<Subnet> findAll();
}
