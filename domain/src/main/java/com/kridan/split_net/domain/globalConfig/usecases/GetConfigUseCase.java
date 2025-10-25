package com.kridan.split_net.domain.globalConfig.usecases;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;

public interface GetConfigUseCase {
    GlobalConfig get(String key);
}
