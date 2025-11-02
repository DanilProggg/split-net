package com.split_net.gateway.domain.peer;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Peer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pubkey;
    private String localIp;

    private Date deleteAt;
}
