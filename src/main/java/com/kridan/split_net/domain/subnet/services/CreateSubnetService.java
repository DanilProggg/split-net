package com.kridan.split_net.domain.subnet.services;

import com.kridan.split_net.domain.subnet.exception.CidrNotValidException;
import com.kridan.split_net.domain.subnet.Subnet;
import com.kridan.split_net.domain.subnet.usecases.CreateSubnetUseCase;
import com.kridan.split_net.domain.subnet.ports.SaveSubnetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
@RequiredArgsConstructor
public class CreateSubnetService implements CreateSubnetUseCase {

    private final SaveSubnetPort saveSubnetPort;

    public boolean isValidCIDR(String cidr) {
        try {
            String[] parts = cidr.split("/");
            if (parts.length != 2) return false;

            // Проверка IP
            InetAddress inet = InetAddress.getByName(parts[0]);
            if (!(inet.getAddress().length == 4)) {
                return false; // только IPv4
            }

            // Проверка префикса
            int prefix = Integer.parseInt(parts[1]);
            return prefix >= 0 && prefix <= 32;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subnet create(String name, String description, String cidr) {

        if(!isValidCIDR(cidr)) throw new CidrNotValidException("Given CIDR is not valid");

        Subnet subnet = new Subnet()
                .setName(name)
                .setDescription(description)
                .setCidr(cidr);
        return saveSubnetPort.save(subnet);
    }
}
