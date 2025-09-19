package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.model.Subnet;

import java.util.List;

public interface GetSubnetsUseCase {
    List<Subnet> getAll();
}
