package com.kridan.split_net.domain.subnet;

import com.kridan.split_net.domain.accessControlRule.AccessControlRule;
import com.kridan.split_net.domain.device.Device;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Subnet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String cidr;

    @OneToMany(mappedBy = "subnet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

    @OneToMany(mappedBy = "subnet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessControlRule> accessControlRules;
}
