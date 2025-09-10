package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import com.kridan.split_net.domain.ports.inbound.GetAllDevicesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@Slf4j
@RequiredArgsConstructor
public class DeviceController {
    private final CreateDeviceUseCase createDeviceUseCase;
    private final GetAllDevicesUseCase getAllDevicesUseCase;


    @PostMapping("/add")
    public ResponseEntity<?> createDevice(@RequestParam("user-id") String uuid, @RequestBody CreateDeviceCommand command) {
        try {
            log.debug("Обращение к endpoint /device/add");
            createDeviceUseCase.createDevice(uuid, command);
            return ResponseEntity.ok("Устройство добавлено");
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getDevices(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaim("email");
        try {
            log.debug("Обращение к endpoint /device/get-all");

            List<Device> devices = getAllDevicesUseCase.devices(email);

            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }
}
