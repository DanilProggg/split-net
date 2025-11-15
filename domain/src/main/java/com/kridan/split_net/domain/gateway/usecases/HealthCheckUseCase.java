package com.kridan.split_net.domain.gateway.usecases;

public interface HealthCheckUseCase {
    void lastSeenUpdate(String gateway_id);
}
