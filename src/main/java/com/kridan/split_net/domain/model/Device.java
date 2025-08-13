package com.kridan.split_net.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private User owner;
}
