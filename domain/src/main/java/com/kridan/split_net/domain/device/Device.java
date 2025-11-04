package com.kridan.split_net.domain.device;

import com.kridan.split_net.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
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

    private Date lastActivation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

}
