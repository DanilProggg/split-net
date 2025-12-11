package com.kridan.split_net.domain.site;

import com.kridan.split_net.domain.gateway.Gateway;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID siteId;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private Date createdAt;


    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Gateway> gateways = new HashSet<>();

    public Site(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = new Date();
    }
}
