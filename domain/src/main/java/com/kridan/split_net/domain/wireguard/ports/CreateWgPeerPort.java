package com.kridan.split_net.domain.wireguard.ports;

import com.kridan.split_net.domain.device.Device;

import java.io.IOException;

public interface CreateWgPeerPort {
    boolean createPeer(Device device) throws IOException, InterruptedException;
}
