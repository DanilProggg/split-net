package com.kridan.split_net.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Device {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String publicKey;

    @Column(unique = true, nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String allowedIps;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}
