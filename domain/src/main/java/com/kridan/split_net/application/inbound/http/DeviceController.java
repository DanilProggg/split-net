package com.kridan.split_net.application.inbound.http;

import com.kridan.split_net.application.inbound.http.dto.CreateDeviceRequest;
import com.kridan.split_net.application.inbound.http.dto.DeviceDto;
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
@RequestMapping("/api/devices")
@Slf4j
@RequiredArgsConstructor
public class DeviceController {
    private final CreateDeviceUseCase createDeviceUseCase;
    private final GetAllDevicesUseCase getAllDevicesUseCase;
    private final FindUserPort findUserPort;
    private final FindDevicePort findDevicePort;


    //Create device
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> createDevice(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateDeviceRequest createDeviceRequest) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            String config = createDeviceUseCase.createDevice(
                    user.getId().toString(),
                    createDeviceRequest.getDeviceName(),
                    createDeviceRequest.getIpAddress()
            );

            return ResponseEntity.ok(config);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }

    //Get all devices of user
    @GetMapping()
    public ResponseEntity<?> getDevices(@AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getClaim("email");

            List<Device> devices = getAllDevicesUseCase.getAllDevices(email);

            List<DeviceDto> deviceDtos = devices.stream()
                    .map(
                        d -> new DeviceDto(
                                d.getId().toString(),
                                d.getName(),
                                d.getPublicKey(),
                                d.getIpAddress()
                        )
                    ).toList();

            return ResponseEntity.ok(deviceDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }


    //Get device of user by uuid
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getDevice(@AuthenticationPrincipal Jwt jwt, @PathVariable String uuid) {
        try {
            String email = jwt.getClaim("email");
            User user = findUserPort.findByEmail(email);

            log.debug("Обращение к endpoint /device/get");

            Device device = findDevicePort.findByOwnerAndId(email, uuid);

            DeviceDto deviceDto = new DeviceDto(
                    device.getId().toString(),
                    device.getName(),
                    device.getPublicKey(),
                    device.getIpAddress()
            );

            return ResponseEntity.ok(deviceDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred");
        }
    }
}
