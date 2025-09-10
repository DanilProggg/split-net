package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.domain.command.CreateDeviceCommand;
import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.inbound.CreateDeviceUseCase;
import com.kridan.split_net.domain.ports.inbound.GetAllDevicesUseCase;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
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
    private final FindUserPort findUserPort;


    @PostMapping("/add")
    public ResponseEntity<?> createDevice(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateDeviceCommand command) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            log.debug("Обращение к endpoint /device/add");
            createDeviceUseCase.createDevice(user.getId().toString(), command);
            return ResponseEntity.ok("Устройство добавлено");
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getDevices(@AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            log.debug("Обращение к endpoint /device/get-all");

            List<Device> devices = getAllDevicesUseCase.getAllDevices(user);

            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("Произошла непредвиденая ошибка");
        }
    }
}
