package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.usecases.GetSubnetsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subnet")
@RequiredArgsConstructor
@Slf4j
public class SubnetController {

    private final GetSubnetsUseCase getSubnetsUseCase;

    @GetMapping("/get-all")
    public ResponseEntity<?> getSubnets(){
        try {
            List<Subnet> subnets = getSubnetsUseCase.getAll();
            return ResponseEntity.ok(subnets);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
