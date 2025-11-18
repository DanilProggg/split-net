package com.kridan.split_net.application.inbound.http.api.gateway;

import com.kridan.split_net.application.inbound.http.api.dto.JwtResponse;
import com.kridan.split_net.application.inbound.http.api.gateway.dto.CreateGatewayRequest;
import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayDto;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import com.kridan.split_net.infrastructure.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gateways")
@Slf4j
@RequiredArgsConstructor
public class GatewayController {

    private final FindAllGatewaysPort findAllGatewaysPort;
    private final JwtUtils jwtUtils;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> createGateway(@RequestBody CreateGatewayRequest createGatewayRequest) {
        try {

            String token = jwtUtils.generateGatewayToken(UUID.randomUUID().toString(), createGatewayRequest.getSite_id());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getGateways() {
        try {
            List<GatewayDto> gatewaysDto = findAllGatewaysPort.findAll().stream()
                    .map(
                            gateway -> new GatewayDto(
                                    gateway.getGatewayId().toString(),
                                    gateway.getName(),
                                    gateway.getWgUrl(),
                                    gateway.getPublicKey(),
                                    gateway.getIpAddress(),
                                    gateway.getLastSeen(),
                                    gateway.getSite().getId()
                            )
                    ).toList();
            return ResponseEntity.ok(gatewaysDto);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }




}
