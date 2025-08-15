package com.kridan.split_net.domain.ports.outbound.wg;

import com.kridan.split_net.domain.model.Device;

import java.io.IOException;

public interface CreateWgPeerPort {
    boolean createPeer(Device device) throws IOException, InterruptedException;
}
