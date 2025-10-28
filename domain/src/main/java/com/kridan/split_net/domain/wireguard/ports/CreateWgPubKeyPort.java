package com.kridan.split_net.domain.wireguard.ports;

import java.io.IOException;

public interface CreateWgPubKeyPort {
    String generatePubKey(String privateKey) throws IOException, InterruptedException;
}
