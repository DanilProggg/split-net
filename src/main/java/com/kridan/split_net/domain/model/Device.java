package com.kridan.split_net.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Device {
    @Id
    private UUID id;
    private String name;
    private String devicePrivateKey;
    private String ipAddress;
    private String allowedIps;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}
