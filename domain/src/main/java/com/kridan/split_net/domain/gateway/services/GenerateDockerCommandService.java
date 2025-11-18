package com.kridan.split_net.domain.gateway.services;

import com.kridan.split_net.domain.gateway.usecases.GenerateDockerCommandUseCase;
import org.springframework.stereotype.Service;

@Service
public class GenerateDockerCommandService implements GenerateDockerCommandUseCase {
    @Override
    public String generate(String token) {
        return String.format("""
                docker run -d \\
                  --restart=unless-stopped \\
                  --pull=always \\
                  --health-cmd="ip link | grep tun-firezone" \\
                  --name=split-net-gateway \\
                  --cap-add=NET_ADMIN \\
                  --sysctl net.ipv4.ip_forward=1 \\
                  --sysctl net.ipv4.conf.all.src_valid_mark=1 \\
                  --device="/dev/net/tun:/dev/net/tun" \\
                  --env TOKEN="%s" \\
                  --env GATEWAY_NAME=$(hostname) \\
                  kridan/split-net/gateway:1
                """, token);
    }
}
