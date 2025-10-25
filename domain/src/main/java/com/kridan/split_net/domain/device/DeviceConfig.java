package com.kridan.split_net.domain.device;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceConfig {
    String privateKey;
    String ipAddress;
    String serverPublicKey;
    String allowedIps;
    String endpoint;
    int port;

    public String toWireguardFormat() {
        return String.format("""
                [Interface]
                PrivateKey = %s
                Address = %s

                [Peer]
                PublicKey = %s
                AllowedIPs = %s
                Endpoint = %s:%d
                """,
                privateKey,
                ipAddress,
                serverPublicKey,
                allowedIps,
                endpoint,
                port
        );
    }
}