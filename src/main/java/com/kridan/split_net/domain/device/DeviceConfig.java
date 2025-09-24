package com.kridan.split_net.domain.device;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceConfig {
    private String devicePrivateKey;
    private String serverPublicKey;
    private String ipAddress;
    private String allowedIps;
    private String serverUri;

    public String generateConfig(){
        String deviceConfig = String.format("""
                    [Interface]
                    PrivateKey = %s
                    Address = %s
            
                    [Peer]
                    PublicKey = %s
                    AllowedIPs = %s
                    Endpoint = %s:%d
                    """,
                devicePrivateKey,
                ipAddress,

                serverPublicKey,
                allowedIps,
                serverUri
        );
        return deviceConfig;
    }

}
