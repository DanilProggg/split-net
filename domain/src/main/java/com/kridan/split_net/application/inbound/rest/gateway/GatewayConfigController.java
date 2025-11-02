package com.kridan.split_net.application.inbound.rest.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config/gateways")
@Slf4j
@RequiredArgsConstructor
public class GatewayConfigController {


    @PostMapping("/health")
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


            return ResponseEntity.ok("TEMP");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
