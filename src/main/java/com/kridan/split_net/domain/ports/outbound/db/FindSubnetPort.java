package com.kridan.split_net.domain.ports.outbound.db;

import com.kridan.split_net.domain.model.Subnet;

import java.util.List;

public interface FindSubnetPort {
    Subnet findByName(String name);
    List<Subnet> findAll();
}
