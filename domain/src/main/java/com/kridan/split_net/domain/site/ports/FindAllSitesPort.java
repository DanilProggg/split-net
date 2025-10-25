package com.kridan.split_net.domain.site.ports;

import com.kridan.split_net.domain.site.Site;

import java.util.List;

public interface FindAllSitesPort {
    List<Site> findAll();
}
