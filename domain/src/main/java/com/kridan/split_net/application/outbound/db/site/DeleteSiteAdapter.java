package com.kridan.split_net.application.outbound.db.site;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.DeleteSitePort;
import com.kridan.split_net.infrastructure.database.repository.site.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteSiteAdapter implements DeleteSitePort {

    private final SiteRepository siteRepository;

    @Override
    public void delete(String siteId) {
        siteRepository.deleteById(UUID.fromString(siteId));
    }
}
