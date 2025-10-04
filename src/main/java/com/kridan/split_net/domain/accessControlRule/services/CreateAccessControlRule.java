package com.kridan.split_net.domain.accessControlRule.services;

import com.kridan.split_net.domain.accessControlRule.ports.CreateAccessControlRulePort;
import com.kridan.split_net.domain.accessControlRule.usecases.AddAccessControlRuleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccessControlRule implements AddAccessControlRuleUseCase {

    private final CreateAccessControlRulePort createAccessControlRulePort;

    @Override
    public boolean create(String destination, String type) {

        //Create and save rule in DB




        //Create rule on machine



    }
}
