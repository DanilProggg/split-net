package com.kridan.split_net.domain.globalConfig.ports;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;

public interface GetGlobalConfigPort {
    GlobalConfig get(String key);
}
