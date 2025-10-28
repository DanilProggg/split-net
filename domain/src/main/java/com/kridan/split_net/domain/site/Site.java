package com.kridan.split_net.domain.site;

import com.kridan.split_net.domain.gateway.Gateway;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false, unique = true)
    private String subnet;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Gateway> gateways = new HashSet<>();

    public Site(String name, String description, String subnet) {
        this.name = name;
        this.description = description;
        this.subnet = subnet;
    }
}
