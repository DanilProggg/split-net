package com.kridan.split_net.domain.services;

import com.kridan.split_net.application.outbound.db.FindSubnetAdapter;
import com.kridan.split_net.domain.model.Subnet;
import com.kridan.split_net.domain.ports.inbound.GetSubnetsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSubnetsService implements GetSubnetsUseCase {

    private final FindSubnetAdapter findSubnetAdapter;
    @Override
    public List<Subnet> getAll() {
        return findSubnetAdapter.findAll();
    }
}
