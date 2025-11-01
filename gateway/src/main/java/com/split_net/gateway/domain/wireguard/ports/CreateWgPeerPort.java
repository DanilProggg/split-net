package com.split_net.gateway.domain.wireguard.ports;

import java.io.IOException;

public interface CreateWgPeerPort {
    boolean createPeer(String pubkey, String ipAddress) throws IOException, InterruptedException;
}
