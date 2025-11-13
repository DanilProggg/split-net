package com.kridan.split_net.domain.site.usecases;

import com.kridan.split_net.domain.site.Site;

public interface GetSiteUseCase {
    Site get(Long id);
}
