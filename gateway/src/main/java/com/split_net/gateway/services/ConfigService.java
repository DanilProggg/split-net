package com.split_net.gateway.services;

import com.split_net.gateway.domain.Config;
import com.split_net.gateway.infrastructure.db.config.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    public String getValue(String key){
        Config config = configRepository.findById(key)
                .orElseThrow(()->new RuntimeException("Config not found"));
        return config.getValue();
    }

    public void save(String key, String value){
        Config config = new Config();
        config.setKey(key);
        config.setValue(value);
        configRepository.save(config);
    }
}
