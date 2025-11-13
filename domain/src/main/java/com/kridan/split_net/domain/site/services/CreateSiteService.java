package com.kridan.split_net.domain.site.services;

import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.globalConfig.ports.GetGlobalConfigPort;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.exception.InvalidSubnetException;
import com.kridan.split_net.domain.site.ports.SaveSitePort;
import com.kridan.split_net.domain.site.usecases.CreateSiteUseCase;
import com.kridan.split_net.domain.site.usecases.GetAllSitesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSiteService implements CreateSiteUseCase {

    private final SaveSitePort saveSitePort;
    private final GetGlobalConfigPort getGlobalConfigPort;
    private final GetAllSitesUseCase getAllSitesUseCase;


    @Override
    public Site create(String name, String description) {


            Site site = new Site(
                    name,
                    description
            );
            return saveSitePort.save(site);


    }
}
