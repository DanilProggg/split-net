package com.kridan.split_net.application.inbound.rest.gateway;

import com.kridan.split_net.application.inbound.rest.dto.JwtResponse;
import com.kridan.split_net.application.inbound.rest.gateway.dto.CreateGatewayRequest;
import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.usecases.CreateGatewayUseCase;
import com.kridan.split_net.infrastructure.database.repository.gateway.GatewayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateway")
@Slf4j
@RequiredArgsConstructor
public class GatewayController {

    private final CreateGatewayUseCase createGatewayUseCase;

    @GetMapping
    public ResponseEntity<?> createGateway(@RequestBody CreateGatewayRequest createGatewayRequest) {
        try {

            //Token for gateways access
            String gatewayToken = createGatewayUseCase.create(createGatewayRequest.getName(), createGatewayRequest.getSite_id());
            return ResponseEntity.ok(new JwtResponse(gatewayToken));

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }


}
