package com.kridan.split_net.domain.gateway.usecases;

public interface HealthCheckUseCase {
    void lastSeenUpdate(Long gateway_id);
}
