package com.kridan.split_net.domain.accessControlRule;

import com.kridan.split_net.domain.group.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccessControlRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination; //CIDR or IP

    private String type; // "allow" or "deny"

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
