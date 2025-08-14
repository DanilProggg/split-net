package com.kridan.split_net.domain.ports.outbound;

import java.io.IOException;

public interface CreateWgPubKeyPort {
    String generatePubKey(String privateKey) throws IOException, InterruptedException;
}
