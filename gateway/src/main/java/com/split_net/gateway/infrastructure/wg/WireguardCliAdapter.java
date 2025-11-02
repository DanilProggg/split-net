package com.split_net.gateway.infrastructure.wg;


import com.split_net.gateway.domain.wireguard.ports.CreateWgPeerPort;
import com.split_net.gateway.infrastructure.shell.ShellCommandExecutor;
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

    @Override
    public boolean createPeer(String pubkey, String ipAddress) throws IOException, InterruptedException {
        /*ShellCommandExecutor shell = new ShellCommandExecutor();

        return shell.run(new String[]{
                "wg", "set", interfaceName,
                "peer", device.getPublicKey(),
                "allowed-ips", device.getIpAddress()
        });*/
        return false;
    }
}
