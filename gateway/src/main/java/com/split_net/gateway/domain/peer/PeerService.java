package com.split_net.gateway.domain.peer;

import com.split_net.gateway.infrastructure.db.peer.PeerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PeerService {

    private final PeerRepository peerRepository;

    Peer create(String pubkey, String localIp, Date deleteAt){
        Peer peer = new Peer();
        peer.setPubkey(pubkey);
        peer.setLocalIp(localIp);
        peer.setDeleteAt(deleteAt);

        return peerRepository.save(peer);
    }

}
