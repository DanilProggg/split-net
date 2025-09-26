package com.kridan.split_net.domain.device.services;

import com.kridan.split_net.domain.device.DeviceConfig;
import com.kridan.split_net.domain.device.DeviceConfigGenerator;
import com.kridan.split_net.domain.device.DeviceFactory;
import com.kridan.split_net.domain.device.Device;
import com.kridan.split_net.domain.device.exception.IpNotValidException;
import com.kridan.split_net.domain.device.usecases.CreateDeviceUseCase;
import com.kridan.split_net.domain.globalConfig.usecases.GetConfigUseCase;
import com.kridan.split_net.domain.device.ports.SaveDevicePort;
import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.ports.FindSubnetPort;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPeerPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.wireguard.ports.CreateWgPubKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDeviceService implements CreateDeviceUseCase {

    private final DeviceFactory deviceFactory;
    private final DeviceConfigGenerator deviceConfigGenerator;
    private final SaveDevicePort saveDevicePort;
    private final CreateWgPrivKeyPort createWgPrivKeyPort;
    private final CreateWgPubKeyPort createWgPubKeyPort;
    private final CreateWgPeerPort createWgPeerPort;
    private final FindSubnetPort findSubnetPort;
    private final FindUserPort findUserPort;

    @Override
    public String createDevice(String userId,
                               String deviceName,
                               String ipAddress,
                               Long subnetId) throws IOException, InterruptedException {
        try {

            Subnet subnet = findSubnetPort.findById(subnetId);
            IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet.getCidr());

            if(!ipAddressMatcher.matches(ipAddress)) throw new IpNotValidException("IP does not belong to CIDR");

            //Device`s private/public key
            String devicePrivateKey = createWgPrivKeyPort.generatePrivKey();
            String devicePublicKey = createWgPubKeyPort.generatePubKey(devicePrivateKey);

            Device device = deviceFactory.create(
                    findUserPort.findById(UUID.fromString(userId)),
                    deviceName,
                    ipAddress,
                    devicePublicKey,
                    subnet
            );

            Device createdDevice = saveDevicePort.save(device);

            //Generate configuration
            DeviceConfig deviceConfig = deviceConfigGenerator.generate(devicePrivateKey, ipAddress);


            //Logging
            Device realDevice = (Device) Hibernate.unproxy(createdDevice);
            log.debug("Добавлено устройство: {}", realDevice.toString());

            //Adding peer
            createWgPeerPort.createPeer(createdDevice);

            return deviceConfig.toWireguardFormat();

        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
