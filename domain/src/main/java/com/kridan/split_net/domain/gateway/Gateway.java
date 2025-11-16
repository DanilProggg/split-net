package com.kridan.split_net.domain.gateway;

import com.kridan.split_net.domain.site.Site;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

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
    @JoinColumn(name = "site_id")
    private Site site;

}


