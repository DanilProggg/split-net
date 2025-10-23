package com.kridan.split_net.application.inbound.rest.gateway;

import com.kridan.split_net.application.inbound.rest.dto.JwtResponse;
import com.kridan.split_net.application.inbound.rest.gateway.dto.CreateGatewayRequest;
import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.gateway.ports.FindAllGatewaysPort;
import com.kridan.split_net.domain.gateway.usecases.CreateGatewayUseCase;
import com.kridan.split_net.domain.site.services.CidrUtils;
import com.kridan.split_net.infrastructure.database.repository.gateway.GatewayRepository;
import com.kridan.split_net.infrastructure.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/api/gateways")
@Slf4j
@RequiredArgsConstructor
public class GatewayController {

    private final CreateGatewayUseCase createGatewayUseCase;
    private final FindAllGatewaysPort findAllGatewaysPort;
    private final JwtUtils jwtUtils;

    @PostMapping()
    public ResponseEntity<?> createGateway(@RequestBody CreateGatewayRequest createGatewayRequest) {
        try {

            //Create gateway
            Gateway gateway = createGatewayUseCase.create(createGatewayRequest.getName(), createGatewayRequest.getSite_id());

            String token = jwtUtils.generateGatewayToken(gateway.getId(), List.of("gateway"));

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getGateways() {
        try {

            //Token for gateways access
            List<Gateway> gateways = findAllGatewaysPort.findAll();
            return ResponseEntity.ok(gateways);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }




}
