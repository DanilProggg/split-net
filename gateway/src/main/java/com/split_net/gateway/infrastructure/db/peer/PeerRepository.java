package com.split_net.gateway.infrastructure.db.peer;

import com.split_net.gateway.domain.peer.Peer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerRepository extends JpaRepository<Peer, Long> {
}
