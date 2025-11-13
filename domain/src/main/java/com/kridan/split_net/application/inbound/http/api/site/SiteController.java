package com.kridan.split_net.application.inbound.http.api.site;

import com.kridan.split_net.application.inbound.http.api.gateway.dto.GatewayDto;
import com.kridan.split_net.application.inbound.http.api.site.dto.CreateSiteRequest;
import com.kridan.split_net.application.inbound.http.api.site.dto.SiteDto;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.usecases.CreateSiteUseCase;
import com.kridan.split_net.domain.site.usecases.GetAllSitesUseCase;
import com.kridan.split_net.domain.site.usecases.GetSiteUseCase;
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
    private final GetSiteUseCase getSiteUseCase;

    @PostMapping()
    public ResponseEntity<?> createSite(@RequestBody CreateSiteRequest createSiteRequest) {
        try {
            Site site = createSiteUseCase.create(
                    createSiteRequest.getName(),
                    createSiteRequest.getDescription()
            );

            return ResponseEntity.ok(site);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getSite(@RequestParam("id") Long id) {
        try {

            Site site = getSiteUseCase.get(id);

            SiteDto siteDto = new SiteDto(
                    site.getId(),
                    site.getName(),
                    site.getDescription(),
                    site.getCreatedAt(),
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
            );


            return ResponseEntity.ok(siteDto);
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
                            site.getCreatedAt(),
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
