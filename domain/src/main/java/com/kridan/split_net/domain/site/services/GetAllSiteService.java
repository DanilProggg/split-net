package com.kridan.split_net.domain.site.services;

import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.FindAllSitesPort;
import com.kridan.split_net.domain.site.ports.FindSitePort;
import com.kridan.split_net.domain.site.usecases.GetAllSitesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSiteService implements GetAllSitesUseCase {

    private final FindAllSitesPort findAllSitesPort;

    @Override
    public List<Site> getAll() {
        return findAllSitesPort.findAll();
    }
}
