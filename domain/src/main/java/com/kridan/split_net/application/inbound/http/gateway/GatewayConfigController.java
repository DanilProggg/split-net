package com.kridan.split_net.application.inbound.http.gateway;

import com.kridan.split_net.domain.gateway.usecases.InitGatewayUseCase;
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

    private final InitGatewayUseCase initGatewayUseCase;

    @PostMapping("/init")
    public ResponseEntity<?> initGateway(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("gateway_url") String gateway_url,
            @RequestParam("pubkey") String gateway_pubkey
    ) {
        try {

            initGatewayUseCase.init(Long.valueOf(jwt.getSubject()), gateway_pubkey, gateway_url);

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getConfig(@AuthenticationPrincipal Jwt jwt) {
        try {

            Long gateway_id = Long.valueOf(jwt.getSubject());


            return ResponseEntity.ok("TEMP");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
