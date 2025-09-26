package com.kridan.split_net.application.inbound.rest;

import com.kridan.split_net.application.inbound.rest.dto.CreateDeviceRequest;
import com.kridan.split_net.application.inbound.rest.dto.DeviceDto;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.device.usecases.GetAllDevicesUseCase;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.device.ports.FindDevicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    private final FindDevicePort findDevicePort;


    @PostMapping(value = "/add", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> createDevice(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateDeviceRequest createDeviceRequest) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            log.debug("Обращение к endpoint /device/add");
            String config = createDeviceUseCase.createDevice(
                    user.getId().toString(),
                    createDeviceRequest.getDeviceName(),
                    createDeviceRequest.getIpAddress(),
                    createDeviceRequest.getSubnetId()
            );

            return ResponseEntity.ok(config);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getDevices(@AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getClaim("email");

            log.debug("Обращение к endpoint /device/get-all");

            List<Device> devices = getAllDevicesUseCase.getAllDevices(email);

            List<DeviceDto> deviceDtos = devices.stream()
                    .map(
                        d -> new DeviceDto(
                                d.getName(),
                                d.getPublicKey(),
                                d.getIpAddress(),
                                d.getSubnet().getId()
                        )
                    ).toList();

            return ResponseEntity.ok(deviceDtos);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<?> getDevices(@AuthenticationPrincipal Jwt jwt, @PathVariable String name) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            log.debug("Обращение к endpoint /device/get");

            Device device = findDevicePort.getDevice(email, name);

            DeviceDto deviceDto = new DeviceDto(
                    device.getName(),
                    device.getPublicKey(),
                    device.getIpAddress(),
                    device.getSubnet().getId()
            );

            return ResponseEntity.ok(deviceDto);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
