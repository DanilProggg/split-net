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

    private String name;

    private String description;

    private String subnet;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Gateway> gateways = new HashSet<>();

    public Site(String name, String subnet) {
        this.name = name;
        this.subnet = subnet;
    }
}
