package com.kridan.split_net.application.inbound.http.api.gateway;

import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayInitRequest;
import com.kridan.split_net.domain.gateway.usecases.HealthCheckUseCase;
import com.kridan.split_net.domain.gateway.usecases.InitGatewayUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateway")
@Slf4j
@RequiredArgsConstructor
public class GatewayConfigController {

    private final InitGatewayUseCase initGatewayUseCase;
    private final HealthCheckUseCase healthCheckUseCase;

    @PostMapping("/init")
    public ResponseEntity<?> initGateway(@AuthenticationPrincipal Jwt jwt, @RequestBody GatewayInitRequest gatewayInitRequest) {
        try {

            initGatewayUseCase.init(Long.valueOf(jwt.getSubject()), gatewayInitRequest.getPublicKey(), gatewayInitRequest.getGatewayUrl());

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/health")
    public ResponseEntity<?> getConfig(@AuthenticationPrincipal Jwt jwt) {
        try {

            Long gateway_id = Long.valueOf(jwt.getSubject());
            healthCheckUseCase.lastSeenUpdate(gateway_id);

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
