package com.kridan.split_net.domain.resource;

import com.kridan.split_net.domain.group.Group;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "group_id")
    private Group group;
}
