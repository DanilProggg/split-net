package com.split_net.gateway.domain;

import org.springframework.stereotype.Component;

@Component
public class GatewayState {
    private volatile boolean initialized = false;
    private volatile boolean healthCheckEnabled = false;

    public synchronized void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public synchronized void setHealthCheckEnabled(boolean enabled) {
        this.healthCheckEnabled = enabled;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isHealthCheckEnabled() {
        return healthCheckEnabled;
    }
}