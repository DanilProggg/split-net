package com.split_net.gateway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "config")
public class Config {
    @Id
    @Column(name = "config_key")
    private String key;
    @Column(name = "config_value")
    private String value;
}
