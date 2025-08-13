package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device")
@Slf4j
public class DeviceController {
    private final CreateDeviceUseCase createDeviceUseCase;

    public DeviceController(CreateDeviceUseCase createDeviceUseCase) {
        this.createDeviceUseCase = createDeviceUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createDevice(@Param("user-id") String uuid, @RequestBody CreateDeviceCommand command) {
        try {
            log.debug("Обращение к endpoint /device/add");
            createDeviceUseCase.createDevice(uuid, command);
            return ResponseEntity.ok("Устройство добавлено");
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }
}
