package com.kridan.split_net.domain.ports.outbound;

import com.kridan.split_net.domain.model.GlobalConfig;

import java.util.Optional;

public interface GetGlobalConfigPort {
    Optional<GlobalConfig> get(String key);
}
