package com.kridan.split_net.domain.site.services;

import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.globalConfig.ports.GetGlobalConfigPort;
import com.kridan.split_net.domain.site.Site;
import com.kridan.split_net.domain.site.ports.SaveSitePort;
import com.kridan.split_net.domain.site.usecases.CreateSiteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class CreateSiteService implements CreateSiteUseCase {

    private final SaveSitePort saveSitePort;
    private final GetGlobalConfigPort getGlobalConfigPort;


    @Override
    public Site create(String name, String subnet) {

        try {
            String globalSubnet = getGlobalConfigPort.get("network").getValue();
            if(!CidrUtils.isSubnetOf(subnet,globalSubnet)){
                throw new RuntimeException("Subnet error");
            }

            Site site = new Site(
                    name,
                    subnet
            );
            return saveSitePort.save(site);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
