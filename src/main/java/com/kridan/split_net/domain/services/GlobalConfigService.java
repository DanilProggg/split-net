package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.model.GlobalConfig;
import com.kridan.split_net.domain.ports.inbound.GetConfigUseCase;
import com.kridan.split_net.domain.ports.inbound.UpdateConfigUseCase;
import com.kridan.split_net.domain.ports.outbound.GetGlobalConfigPort;
import com.kridan.split_net.domain.ports.outbound.SaveGlobalConfigPort;
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
