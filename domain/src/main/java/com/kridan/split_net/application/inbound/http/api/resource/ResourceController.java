package com.kridan.split_net.application.inbound.http.api.resource;

import com.kridan.split_net.application.inbound.http.api.resource.dto.CreateResourceRequest;
import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.usecases.CreateResourceUseCase;
import com.kridan.split_net.domain.resource.usecases.GetAllResourcesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Slf4j
@RequiredArgsConstructor
public class ResourceController {

    private final CreateResourceUseCase createResourceUseCase;
    private final GetAllResourcesUseCase getAllResourcesUseCase;

    @PostMapping()
    public ResponseEntity<?> createResource(@RequestBody CreateResourceRequest createResourceRequest) {
        try {

            Resource resource = createResourceUseCase.create(
                    createResourceRequest.getDestination(),
                    createResourceRequest.getGroup_id());

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }


    @GetMapping()
    public ResponseEntity<?> getResources() {
        try {
            List<Resource> resources = getAllResourcesUseCase.getAll();

            return ResponseEntity.ok(resources);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
