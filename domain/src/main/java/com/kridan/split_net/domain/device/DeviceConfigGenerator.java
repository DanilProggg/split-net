package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.globalConfig.ports.GetGlobalConfigPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceConfigGenerator {

    private final GetGlobalConfigPort getGlobalConfigPort;

    public DeviceConfig generate(String devicePrivateKey, String ipAddress) {
        return DeviceConfig.builder()
                .privateKey(devicePrivateKey)
                .ipAddress(ipAddress)
                .serverPublicKey(getGlobalConfigPort.get("publicKey").getValue())
                .allowedIps(getGlobalConfigPort.get("default_allowed_ips").getValue())
                .endpoint(getGlobalConfigPort.get("url").getValue())
                .port(Integer.parseInt(getGlobalConfigPort.get("port").getValue()))
                .build();
    }

}
