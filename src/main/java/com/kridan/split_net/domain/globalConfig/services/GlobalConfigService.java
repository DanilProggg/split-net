package com.kridan.split_net.domain.globalConfig.services;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;
import com.kridan.split_net.domain.globalConfig.usecases.GetConfigUseCase;
import com.kridan.split_net.domain.globalConfig.usecases.UpdateConfigUseCase;
import com.kridan.split_net.domain.globalConfig.ports.GetGlobalConfigPort;
import com.kridan.split_net.domain.globalConfig.ports.SaveGlobalConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalConfigService implements UpdateConfigUseCase, GetConfigUseCase {

    private final SaveGlobalConfigPort saveGlobalConfigPort;
    private final GetGlobalConfigPort getGlobalConfigPort;

    @Override
    public GlobalConfig update(String key, String value) {
        return saveGlobalConfigPort.save(key, value);
    }

    @Override
    public GlobalConfig get(String key) {
        return getGlobalConfigPort.get(key);
    }
}
