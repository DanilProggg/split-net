package com.kridan.split_net.application.inbound.rest.gateway;

import com.kridan.split_net.application.inbound.rest.gateway.dto.GatewayInitRequest;
import com.kridan.split_net.domain.gateway.GatewayConfig.GatewayConfig;
import com.kridan.split_net.domain.gateway.usecases.GenerateConfigUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateways/config")
@Slf4j
@RequiredArgsConstructor
public class GatewayConfigController {

    private final GenerateConfigUseCase generateConfigUseCase;

    @PostMapping()
    public ResponseEntity<?> initGateway(@AuthenticationPrincipal Jwt jwt) {
        try {


            return ResponseEntity.ok("Get config");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getConfig(@AuthenticationPrincipal Jwt jwt) {
        try {

            Long gateway_id = Long.valueOf(jwt.getSubject());

            GatewayConfig gatewayConfig = generateConfigUseCase.generate(gateway_id);

            return ResponseEntity.ok("Get config");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
