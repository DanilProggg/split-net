package com.kridan.split_net.application.outbound.wg;

import com.kridan.split_net.domain.ports.outbound.CreateWgPrivKeyPort;
import com.kridan.split_net.domain.ports.outbound.CreateWgPubKeyPort;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class WgTools implements CreateWgPrivKeyPort, CreateWgPubKeyPort {
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generatePrivKey() {
        byte[] privateKeyBytes = new byte[32];
        random.nextBytes(privateKeyBytes);

        // Маскирование приватного ключа (clamping)
        privateKeyBytes[0] &= 248;
        privateKeyBytes[31] &= 127;
        privateKeyBytes[31] |= 64;

        return Base64.getEncoder().withoutPadding().encodeToString(privateKeyBytes);
    }

    @Override
    public String generatePubKey(String base64PrivateKey) {
        byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
        X25519PrivateKeyParameters privateKey = new X25519PrivateKeyParameters(privateKeyBytes, 0);
        X25519PublicKeyParameters publicKey = privateKey.generatePublicKey();

        return Base64.getEncoder().withoutPadding().encodeToString(publicKey.getEncoded());
    }
}
