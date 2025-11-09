package com.kridan.split_net.application.inbound.http_api.site;

import com.kridan.split_net.application.inbound.http_api.gateway.dto.GatewayDto;
import com.kridan.split_net.application.inbound.http_api.site.dto.CreateSiteRequest;
import com.kridan.split_net.application.inbound.http_api.site.dto.SiteDto;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.usecases.CreateSiteUseCase;
import com.kridan.split_net.domain.site.usecases.GetAllSitesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
@Slf4j
@RequiredArgsConstructor
public class SiteController {

    private final CreateSiteUseCase createSiteUseCase;
    private final GetAllSitesUseCase  getAllSitesUseCase;

    @PostMapping()
    public ResponseEntity<?> createSite(@RequestBody CreateSiteRequest createSiteRequest) {
        try {
            Site site = createSiteUseCase.create(
                    createSiteRequest.getName(),
                    createSiteRequest.getDescription(),
                    createSiteRequest.getSubnet()
            );

            return ResponseEntity.ok(site);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getSites() {
        try {

            List<SiteDto> siteDtos = getAllSitesUseCase.getAll().stream()
                    .map(site -> new SiteDto(
                            site.getId(),
                            site.getName(),
                            site.getDescription(),
                            site.getSubnet(),
                            site.getGateways().stream().map(
                                    gateway -> new GatewayDto(
                                            gateway.getId(),
                                            gateway.getName(),
                                            gateway.getWgUrl(),
                                            gateway.getPublicKey(),
                                            gateway.getIpAddress(),
                                            gateway.getLastSeen(),
                                            gateway.getSite().getId()
                                    )
                            ).toList()
                    )).toList();

            return ResponseEntity.ok(siteDtos);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
