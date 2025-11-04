package com.kridan.split_net.application.outbound.rabbitmq;

import com.kridan.split_net.domain.device.Peer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private Peer peer;
    //Update | Delete | Add
    private String action;
}
