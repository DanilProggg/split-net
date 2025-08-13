package com.kridan.split_net.domain.ports.outbound;

import com.kridan.split_net.domain.model.Device;

public interface CreateWgPeerPort {
    boolean createPeer(Device device);
}
