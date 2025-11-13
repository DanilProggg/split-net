package com.kridan.split_net.domain.site.usecases;

import com.kridan.split_net.domain.site.Site;

public interface CreateSiteUseCase {
    Site create(String name, String description);
}
