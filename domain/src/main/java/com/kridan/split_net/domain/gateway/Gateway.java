package com.kridan.split_net.domain.gateway;

import com.kridan.split_net.domain.site.Site;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gateway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Gateway(String name, String ipAddress, Site site) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.site = site;
    }
}
