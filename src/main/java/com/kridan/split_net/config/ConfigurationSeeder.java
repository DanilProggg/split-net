package com.kridan.split_net.config;

import com.kridan.split_net.domain.globalConfig.ports.SaveGlobalConfigPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationSeeder implements CommandLineRunner {

    private final SaveGlobalConfigPort saveGlobalConfigPort;
    private final String url;
    private final int port;

    public ConfigurationSeeder(@Value("${wg.url}") String url, @Value("${wg.port}") int port, SaveGlobalConfigPort saveGlobalConfigPort) {
        this.saveGlobalConfigPort = saveGlobalConfigPort;
        this.url = url;
        this.port = port;
    }

    @Override
    public void run(String... args) {
        seedConfig("default_allowed_ips", "100.64.0.0/10");
        seedConfig("url", url);
        seedConfig("port", String.valueOf(port));
        seedConfig("max_devices_per_user", "10");
    }

    private void seedConfig(String key, String value) {
        saveGlobalConfigPort.save(key, value);
    }
}

