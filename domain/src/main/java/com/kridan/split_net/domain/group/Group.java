package com.kridan.split_net.domain.group;

import com.kridan.split_net.domain.accessControlRule.AccessControlRule;
import com.kridan.split_net.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessControlRule> rules = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",                // имя промежуточной таблицы
            joinColumns = @JoinColumn(name = "user_id"), // внешний ключ на таблицу Student
            inverseJoinColumns = @JoinColumn(name = "group_id") // внешний ключ на таблицу Course
    )
    private Set<User> users = new HashSet<>();


    //Constructor for group create
    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
