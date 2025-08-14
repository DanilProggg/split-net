package com.kridan.split_net.application.outbound.wg;

import com.kridan.split_net.domain.model.Device;
import com.kridan.split_net.domain.ports.outbound.CreateWgPeerPort;
import com.kridan.split_net.domain.ports.outbound.CreateWgPubKeyPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class CreateWgPeerComponent implements CreateWgPeerPort {

    public final CreateWgPubKeyPort createWgPubKeyPort;

    public CreateWgPeerComponent(CreateWgPubKeyPort createWgPubKeyPort) {
        this.createWgPubKeyPort = createWgPubKeyPort;
    }
    //Добавление пира через shell

    @Value("${wg.interface}")
    private String interfaceName;

    @Override
    public boolean createPeer(Device device) throws IOException {
        return runWgCommand(new String[]{
                "wg", "set", interfaceName,
                "peer", createWgPubKeyPort.generatePubKey(device.getDevicePrivateKey()),
                "allowed-ips", device.getAllowedIps()
        });
    }

    /**
     * Запуск команды и вывод stdout/stderr
     */
    private boolean runWgCommand(String[] command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
            }

            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
