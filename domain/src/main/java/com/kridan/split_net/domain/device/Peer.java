package com.kridan.split_net.domain.device;

import lombok.Data;

import java.util.Date;

@Data
public class Peer {
    private String pubkey;
    private String ip;
    private Date expiredAt;
}
