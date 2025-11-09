package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.gateway.Gateway;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DeviceConfig {
    String privateKey;
    String ipAddress;
    List<Gateway> gateways;

    public String toWireguardFormat() {
        StringBuilder config = new StringBuilder();

        // Секция [Interface]
        config.append(String.format("""
                [Interface]
                PrivateKey = %s
                Address = %s
                
                """,
                privateKey,
                ipAddress
        ));

        // Секции [Peer] для каждого шлюза
        for (Gateway gateway : gateways) {
            config.append(String.format("""
                [Peer]
                PublicKey = %s
                AllowedIPs = %s
                Endpoint = %s
                
                """,
                    gateway.getPublicKey(),
                    gateway.getSite().getSubnet(), // Предполагается, что у Gateway есть это поле
                    gateway.getWgUrl()
            ));
        }

        return config.toString();
    }
}
