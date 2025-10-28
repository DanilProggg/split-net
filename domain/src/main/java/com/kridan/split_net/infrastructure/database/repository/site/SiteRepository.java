package com.kridan.split_net.infrastructure.database.repository.site;

import com.kridan.split_net.domain.site.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findByName(String name);
}
