package com.kridan.split_net.application.inbound.http.api.gateway;

import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayInitRequest;
import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayInitResponse;
import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindGatewayPort;
import com.kridan.split_net.domain.gateway.usecases.CreateGatewayUseCase;
import com.kridan.split_net.domain.gateway.usecases.HealthCheckUseCase;
import com.kridan.split_net.domain.gateway.usecases.InitGatewayUseCase;
import com.kridan.split_net.domain.gateway.usecases.RefreshGatewayInfoUseCase;
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
    private final FindGatewayPort findGatewayPort;
    private final CreateGatewayUseCase createGatewayUseCase;
    private final RefreshGatewayInfoUseCase refreshGatewayInfoUseCase;

    @PostMapping("/init")
    public ResponseEntity<?> initGateway(@AuthenticationPrincipal Jwt jwt, @RequestBody GatewayInitRequest gatewayInitRequest) {
        try {

            log.debug("Request gateway init: {}", gatewayInitRequest);

            try {
                Gateway gateway = findGatewayPort.findById(jwt.getSubject()); //Error if gateway not found
                refreshGatewayInfoUseCase.refresh(
                        jwt.getSubject(),
                        gatewayInitRequest.getHostname(),
                        gatewayInitRequest.getPublicKey(),
                        gatewayInitRequest.getGatewayUrl()
                );

                log.debug("Gateway found {}. Refresh info", jwt.getSubject());
                return ResponseEntity.ok(new GatewayInitResponse(gateway.getIpAddress()));

            } catch (Exception e) {
                Gateway gateway = createGatewayUseCase.create(
                        jwt.getSubject(),
                        gatewayInitRequest.getHostname(),
                        jwt.getClaim("site")
                );

                initGatewayUseCase.init(
                        jwt.getSubject(),
                        gatewayInitRequest.getHostname(),
                        gatewayInitRequest.getPublicKey(),
                        gatewayInitRequest.getGatewayUrl()
                );

                log.debug("Gateway not found. Register gateway {}", jwt.getSubject());
                return ResponseEntity.ok(new GatewayInitResponse(gateway.getIpAddress()));
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping("/health")
    public ResponseEntity<?> getConfig(@AuthenticationPrincipal Jwt jwt) {
        try {

            String gateway_id = jwt.getSubject();
            healthCheckUseCase.lastSeenUpdate(gateway_id);

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
