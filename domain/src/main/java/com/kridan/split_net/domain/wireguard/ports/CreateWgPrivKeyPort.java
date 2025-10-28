package com.kridan.split_net.domain.wireguard.ports;

import java.io.IOException;

public interface CreateWgPrivKeyPort {
    String generatePrivKey() throws IOException, InterruptedException;
}
