package com.split_net.gateway.domain.wireguard.ports;

import java.io.IOException;

public interface CreateWgPubKeyPort {
    String generatePubKey(String privateKey) throws IOException, InterruptedException;
}
