package com.kridan.split_net.domain.device;

public class DeviceConfigGenerator {


    private String devicePrivateKey;
    private String serverPublicKey;
    private String defaultAllowedIps;
    private String ipAddress;
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
                defaultAllowedIps,
                serverPublicKey,
                serverUri
        );
        return deviceConfig;
    }

}
