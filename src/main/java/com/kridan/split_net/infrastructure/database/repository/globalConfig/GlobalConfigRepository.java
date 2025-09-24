package com.kridan.split_net.infrastructure.database.repository.globalConfig;

import com.kridan.split_net.domain.globalConfig.GlobalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalConfigRepository extends JpaRepository<GlobalConfig, String> {
}
