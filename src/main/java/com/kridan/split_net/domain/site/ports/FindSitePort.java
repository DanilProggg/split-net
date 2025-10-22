package com.kridan.split_net.domain.site.ports;

import com.kridan.split_net.domain.site.Site;

public interface FindSitePort {
    Site findById(Long id);
    Site findByName(String name);
}
