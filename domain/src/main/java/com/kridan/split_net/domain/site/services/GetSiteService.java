package com.kridan.split_net.domain.site.services;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import com.kridan.split_net.domain.site.usecases.GetSiteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSiteService implements GetSiteUseCase {

    private final FindSitePort findSitePort;

    @Override
    public Site get(Long id) {
        return findSitePort.findById(id);
    }
}
