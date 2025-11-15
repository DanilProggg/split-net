package com.kridan.split_net.domain.resource;

import com.kridan.split_net.domain.policy.Policy;
import com.kridan.split_net.domain.site.Site;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String destination; //CIDR or IP

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Site site;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Policy> policies = new HashSet<>();
}
