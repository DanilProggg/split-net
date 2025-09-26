package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.application.inbound.rest.dto.CreateSubnetRequest;
import com.kridan.split_net.application.inbound.rest.dto.SubnetDto;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.usecases.CreateSubnetUseCase;
import com.kridan.split_net.domain.subnet.usecases.GetSubnetsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subnets")
@RequiredArgsConstructor
@Slf4j
public class SubnetController {

    private final CreateSubnetUseCase createSubnetUseCase;
    private final GetSubnetsUseCase getSubnetsUseCase;

    @GetMapping()
    public ResponseEntity<?> getSubnets(){
        try {
            List<Subnet> subnets = getSubnetsUseCase.getAll();
            List<SubnetDto> subnetDtos = subnets.stream()
                    .map(
                            s -> new SubnetDto(
                                    s.getName(),
                                    s.getDescription(),
                                    s.getCidr(),
                                    s.getDevices().stream().map(d->d.getId().toString()).toList()
                            )
                    ).toList();


            return ResponseEntity.ok(subnetDtos);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @PostMapping()
    public ResponseEntity<?> createSubnet(@RequestBody CreateSubnetRequest createSubnetRequest){
        try {
            Subnet subnet = createSubnetUseCase.create(
                    createSubnetRequest.getName(),
                    createSubnetRequest.getDescription(),
                    createSubnetRequest.getCidr()
            );
            log.debug("Subnet with CIDR: {} created", subnet.getCidr());
            return ResponseEntity.ok("Subnet created successful");
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
