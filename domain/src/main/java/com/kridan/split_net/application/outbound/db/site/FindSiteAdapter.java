package com.kridan.split_net.application.outbound.db.site;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindAllSitesPort;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import com.kridan.split_net.infrastructure.database.repository.site.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSiteAdapter implements FindSitePort, FindAllSitesPort {

    private final SiteRepository siteRepository;

    @Override
    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Override
    public Site findById(Long id) {
        return siteRepository.findById(id).orElseThrow(
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
