package com.kridan.split_net.domain.site.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CidrUtils {

    public static boolean isValidCIDR(String cidr) {
        try {
            if (cidr == null || !cidr.contains("/")) return false;

            String[] parts = cidr.split("/");
            if (parts.length != 2) return false;

            InetAddress address = InetAddress.getByName(parts[0]);
            int prefix = Integer.parseInt(parts[1]);

            if (address.getAddress().length == 4) { // IPv4
                return prefix >= 0 && prefix <= 32;
            } else { // IPv6
                return prefix >= 0 && prefix <= 128;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSubnetOf(String childCidr, String parentCidr) throws UnknownHostException {
        // Проверяем валидность
        if (!isValidCIDR(childCidr) || !isValidCIDR(parentCidr)) return false;

        // Разбираем CIDR
        String[] childParts = childCidr.split("/");
        String[] parentParts = parentCidr.split("/");

        InetAddress childAddress = InetAddress.getByName(childParts[0]);
        InetAddress parentAddress = InetAddress.getByName(parentParts[0]);

        int childPrefix = Integer.parseInt(childParts[1]);
        int parentPrefix = Integer.parseInt(parentParts[1]);

        if (childAddress.getAddress().length != parentAddress.getAddress().length)
            return false; // IPv4 vs IPv6 mismatch

        byte[] childBytes = childAddress.getAddress();
        byte[] parentBytes = parentAddress.getAddress();

        // Сравниваем только первые "parentPrefix" бит
        int fullBytes = parentPrefix / 8;
        int remainingBits = parentPrefix % 8;

        // Проверяем полные байты
        for (int i = 0; i < fullBytes; i++) {
            if (childBytes[i] != parentBytes[i]) return false;
        }

        // Проверяем частичный байт
        if (remainingBits > 0) {
            int mask = 0xFF << (8 - remainingBits);
            if ((childBytes[fullBytes] & mask) != (parentBytes[fullBytes] & mask)) return false;
        }
        return true;
    }
}