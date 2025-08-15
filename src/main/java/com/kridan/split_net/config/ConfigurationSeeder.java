package com.kridan.split_net.config;

import com.kridan.split_net.domain.ports.outbound.GetGlobalConfigPort;
import com.kridan.split_net.domain.ports.outbound.SaveGlobalConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.module.Configuration;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Component
@RequiredArgsConstructor
public class ConfigurationSeeder implements CommandLineRunner {

    private final GetGlobalConfigPort getGlobalConfigPort;
    private final SaveGlobalConfigPort saveGlobalConfigPort;

    @Override
    public void run(String... args) {
        seedConfig("default_allowed_ips", "0.0.0.0/0");
        seedConfig("api_base_url", "https://example.com/api");
        seedConfig("max_devices_per_user", "10");
    }

    private void seedConfig(String key, String value) {
        if(getGlobalConfigPort.get(key).isEmpty()) {
            saveGlobalConfigPort.save(key, value);
        }
    }
}

