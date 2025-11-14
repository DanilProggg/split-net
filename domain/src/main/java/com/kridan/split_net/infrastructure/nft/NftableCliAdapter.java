package com.kridan.split_net.infrastructure.nft;

import com.kridan.split_net.domain.resource.Resource;
import com.kridan.split_net.domain.resource.ports.SaveResourcePort;
import com.kridan.split_net.infrastructure.shell.ShellCommandExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NftableCliAdapter implements SaveResourcePort {

    private final ShellCommandExecutor shellCommandExecutor;
    //Добавление пира через shell

    @Override
    public boolean create(Resource resource) {

        ShellCommandExecutor shell = new ShellCommandExecutor();

        return shell.run(new String[]{
            ""
        });
    }
}
