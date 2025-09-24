package com.kridan.split_net.domain.subnet.services;

import com.kridan.split_net.application.outbound.db.FindSubnetAdapter;
import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.usecases.GetSubnetsUseCase;
import lombok.RequiredArgsConstructor;
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
