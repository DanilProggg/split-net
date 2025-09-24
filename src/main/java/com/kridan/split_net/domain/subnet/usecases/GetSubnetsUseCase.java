package com.kridan.split_net.domain.subnet.usecases;

import com.kridan.split_net.domain.subnet.Subnet;

import java.util.List;

public interface GetSubnetsUseCase {
    List<Subnet> getAll();
}
