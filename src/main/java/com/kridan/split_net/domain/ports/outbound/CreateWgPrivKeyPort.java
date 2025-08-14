package com.kridan.split_net.domain.ports.outbound;

import java.io.IOException;

public interface CreateWgPrivKeyPort {
    String generatePrivKey() throws IOException, InterruptedException;
}
