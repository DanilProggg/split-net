package com.kridan.split_net.config;

import com.kridan.split_net.domain.ports.outbound.GetGlobalConfigPort;
import com.kridan.split_net.domain.ports.outbound.SaveGlobalConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.module.Configuration;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Component
public class ConfigurationSeeder implements CommandLineRunner {

    private final SaveGlobalConfigPort saveGlobalConfigPort;
    private final String url;

    public ConfigurationSeeder(@Value("${wg.url}") String url, SaveGlobalConfigPort saveGlobalConfigPort) {
        this.saveGlobalConfigPort = saveGlobalConfigPort;
        this.url = url;
    }

    @Override
    public void run(String... args) {
        seedConfig("default_allowed_ips", "0.0.0.0/0");
        seedConfig("url", url);
        seedConfig("max_devices_per_user", "10");
    }

    private void seedConfig(String key, String value) {
        saveGlobalConfigPort.save(key, value);
    }
}

