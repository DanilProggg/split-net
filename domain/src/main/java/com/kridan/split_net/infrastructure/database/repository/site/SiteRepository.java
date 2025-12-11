package com.kridan.split_net.infrastructure.database.repository.site;

import com.kridan.split_net.domain.site.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {
    Optional<Site> findByName(String name);

}
