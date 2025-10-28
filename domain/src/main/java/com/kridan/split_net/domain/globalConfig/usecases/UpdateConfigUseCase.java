package com.kridan.split_net.domain.globalConfig.usecases;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;

public interface UpdateConfigUseCase {
    GlobalConfig update(String key, String value);
}
