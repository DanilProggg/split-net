package com.kridan.split_net.domain.value;

import jakarta.persistence.Column;

public record DeviceConfig(
    String privateKey,
    String publicKey,
    String ipAddress,
    String allowedIps
) {}
