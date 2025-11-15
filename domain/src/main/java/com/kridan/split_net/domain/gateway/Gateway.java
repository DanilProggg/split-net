package com.kridan.split_net.domain.gateway;

import com.kridan.split_net.domain.site.Site;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gateway {
    @Id
    private UUID gatewayId;

    @Column(nullable = false, unique = true)
    private String name;

    private String wgUrl;

    @Column(unique = true)
    private String publicKey;

    private String ipAddress;

    private Date lastSeen;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Site site;

    public Gateway(UUID gatewayId, String name, String ipAddress, Site site) {
        this.gatewayId = gatewayId;
        this.name = name;
        this.ipAddress = ipAddress;
        this.site = site;
    }
}
