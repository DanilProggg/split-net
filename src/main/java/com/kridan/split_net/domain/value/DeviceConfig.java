package com.kridan.split_net.domain.value;

public record DeviceConfig(
    String privateKey,
    String publicKey,
    String ipAddress,
    String allowedIps
) {}
