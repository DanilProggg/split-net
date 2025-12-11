package com.kridan.split_net.application.outbound.db.site;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindAllSitesPort;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import com.kridan.split_net.infrastructure.database.repository.site.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindSiteAdapter implements FindSitePort, FindAllSitesPort {

    private final SiteRepository siteRepository;

    @Override
    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Override
    public Site findById(String siteId) {
        return siteRepository.findById(UUID.fromString(siteId)).orElseThrow(
                ()->new RuntimeException("Site with given ID not found")
        );
    }

    @Override
    public Site findByName(String name) {
        return siteRepository.findByName(name).orElseThrow(
                ()->new RuntimeException("Site with given NAME not found")
        );
    }
}
