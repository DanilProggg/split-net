package com.kridan.split_net.domain.ports.inbound;

import com.kridan.split_net.domain.model.GlobalConfig;

public interface GetConfigUseCase {
    GlobalConfig get(String key);
}
