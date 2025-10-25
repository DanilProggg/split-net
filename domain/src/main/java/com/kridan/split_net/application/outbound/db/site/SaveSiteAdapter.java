package com.kridan.split_net.application.outbound.db.site;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.SaveSitePort;
import com.kridan.split_net.infrastructure.database.repository.site.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveSiteAdapter implements SaveSitePort {

    private final SiteRepository siteRepository;

    @Override
    public Site save(Site site) {
        return siteRepository.save(site);
    }
}
