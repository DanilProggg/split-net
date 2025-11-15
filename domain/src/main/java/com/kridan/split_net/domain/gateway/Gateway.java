package com.kridan.split_net.domain.gateway;

import com.kridan.split_net.domain.site.Site;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
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

}
