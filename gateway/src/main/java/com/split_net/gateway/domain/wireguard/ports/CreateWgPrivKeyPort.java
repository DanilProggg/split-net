package com.split_net.gateway.domain.wireguard.ports;

import java.io.IOException;

public interface CreateWgPrivKeyPort {
    String generatePrivKey() throws IOException, InterruptedException;
}
