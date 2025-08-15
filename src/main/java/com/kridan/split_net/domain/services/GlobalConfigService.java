package com.kridan.split_net.domain.services;

import com.kridan.split_net.domain.model.GlobalConfig;
import com.kridan.split_net.domain.ports.inbound.UpdateConfigUseCase;
import com.kridan.split_net.domain.ports.outbound.SaveGlobalConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalConfigService implements UpdateConfigUseCase {

    public final SaveGlobalConfigPort saveGlobalConfigPort;

    @Override
    public GlobalConfig update(String key, String value) {
        return saveGlobalConfigPort.save(key, value);
    }
}
