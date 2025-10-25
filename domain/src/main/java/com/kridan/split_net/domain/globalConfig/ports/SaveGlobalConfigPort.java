package com.kridan.split_net.domain.globalConfig.ports;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;

public interface SaveGlobalConfigPort {
    GlobalConfig save(String key, String value);
}
