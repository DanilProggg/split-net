package com.kridan.split_net.infrastructure.nft;

import com.kridan.split_net.domain.accessControlRule.AccessControlRule;
import com.kridan.split_net.domain.accessControlRule.ports.CreateAccessControlRulePort;
import com.kridan.split_net.infrastructure.shell.ShellCommandExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NftableCliAdapter implements CreateAccessControlRulePort {

    private final ShellCommandExecutor shellCommandExecutor;
    //Добавление пира через shell

    @Override
    public boolean create(AccessControlRule accessControlRule) {

        ShellCommandExecutor shell = new ShellCommandExecutor();

        return shell.run(new String[]{
            ""
        });
    }
}
