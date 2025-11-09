package com.split_net.gateway.services;

import com.split_net.gateway.domain.Peer;
import com.split_net.gateway.infrastructure.db.peer.PeerRepository;
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
        peer.setIp(localIp);
        peer.setExpiredAt(deleteAt);

        return peerRepository.save(peer);
    }



}
