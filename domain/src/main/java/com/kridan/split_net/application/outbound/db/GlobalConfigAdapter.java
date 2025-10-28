package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;
import com.kridan.split_net.domain.globalConfig.ports.GetGlobalConfigPort;
import com.kridan.split_net.domain.globalConfig.ports.SaveGlobalConfigPort;
import com.kridan.split_net.infrastructure.database.repository.globalConfig.GlobalConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalConfigAdapter implements SaveGlobalConfigPort, GetGlobalConfigPort {
    private final GlobalConfigRepository globalConfigRepository;

    @Override
    public GlobalConfig get(String key) {
        GlobalConfig config = globalConfigRepository.findById(key).orElseThrow(()->new RuntimeException("Global config not found"));
        return config;
    }

    @Override
    public GlobalConfig save(String key, String value) {
        GlobalConfig config = new GlobalConfig();
        config.setKey(key);
        config.setValue(value);
        GlobalConfig savedConfig = globalConfigRepository.save(config);
        return savedConfig;
    }
}
