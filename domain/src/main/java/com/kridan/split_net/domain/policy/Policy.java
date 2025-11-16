package com.kridan.split_net.domain.policy;

import com.kridan.split_net.domain.group.Group;
import com.kridan.split_net.domain.resource.Resource;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {
    @Id
    private UUID policyId;

    // Policy относится к одному Resource
    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    // Policy может иметь несколько Groups
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    private String description;
}
