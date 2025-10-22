package com.kridan.split_net.application.inbound.rest.gateway;

import com.kridan.split_net.application.inbound.rest.gateway.dto.GatewayInitRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway/config")
@Slf4j
@RequiredArgsConstructor
public class GatewayConfigController {
    @PostMapping()
    public ResponseEntity<?> initGateway(@RequestBody GatewayInitRequest gatewayInitRequest) {
        try {

            return ResponseEntity.ok("Get config");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
