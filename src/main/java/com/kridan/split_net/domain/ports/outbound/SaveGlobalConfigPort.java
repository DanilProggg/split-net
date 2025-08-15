package com.kridan.split_net.domain.ports.outbound;

import com.kridan.split_net.domain.model.GlobalConfig;

public interface SaveGlobalConfigPort {
    GlobalConfig save(String key, String value);
}
