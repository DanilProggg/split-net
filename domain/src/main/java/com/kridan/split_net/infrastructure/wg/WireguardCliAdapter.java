package com.kridan.split_net.infrastructure.wg;

import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPeerPort;
import com.kridan.split_net.infrastructure.shell.ShellCommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class WireguardCliAdapter implements CreateWgPeerPort {

    private final ShellCommandExecutor shellCommandExecutor;
    //Добавление пира через shell

    @Value("${wg.interface}")
    private String interfaceName;

    @Override
    public boolean createPeer(Device device) throws IOException, InterruptedException {
        ShellCommandExecutor shell = new ShellCommandExecutor();

        return shell.run(new String[]{
                "wg", "set", interfaceName,
                "peer", device.getPublicKey(),
                "allowed-ips", device.getIpAddress()
        });
    }
}
