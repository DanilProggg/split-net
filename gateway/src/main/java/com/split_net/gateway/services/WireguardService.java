package com.split_net.gateway.services;

import com.split_net.gateway.config.JwtConfig;
import com.split_net.gateway.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.split_net.gateway.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class WireguardService {

    @Value("${gateway.wg.interface.ip}")
    private final String ip;

    @Value("${gateway.wg.interface.port}")
    private final int port;

    private final JwtConfig jwtConfig;
    private final ConfigService configService;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;


    public void setup() throws IOException, InterruptedException {

        String privateKey;

        try{
            privateKey = configService.getValue("privateKey");
            log.info("Key already exists");
        } catch (Exception e){
            //Gen new key
            privateKey = createWgPrivKeyPort.generatePrivKey();
            configService.save("privateKey", privateKey);

            String publicKey = createWgPubKeyPort.generatePubKey(privateKey);
            configService.save("publicKey", publicKey);

            log.info("Private and public have been generated and saved");
        }


        new ProcessBuilder("ip", "link", "add", "wg0", "type", "wireguard")
                .inheritIO()
                .start()
                .waitFor();

        // Configure private key and port
        new ProcessBuilder("wg", "set", "wg0",
                "listen-port", String.valueOf(port),
                "private-key", privateKey)
                .inheritIO()
                .start()
                .waitFor();

        // Set IP address
        new ProcessBuilder("ip", "addr", "add", jwtConfig.getParamAsString("ip") , "dev", "wg0")
                .inheritIO()
                .start()
                .waitFor();


        // Interface link up
        new ProcessBuilder("ip", "link", "set", "up", "dev", "wg0")
                .inheritIO()
                .start()
                .waitFor();
    }
}
