package com.kridan.split_net.domain.ports.outbound;

public interface CreateWgPubKeyPort {
    String generatePubKey(String privateKey);
}
