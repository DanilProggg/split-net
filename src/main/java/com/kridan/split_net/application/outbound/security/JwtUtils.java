package com.kridan.split_net.application.outbound.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtEncoder jwtEncoder;

    public String generateToken(String username, List<String> roles) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")                // кто выдал токен
                .issuedAt(now)                 // время создания
                .expiresAt(now.plus(1, ChronoUnit.DAYS)) // срок действия
                .subject(username)             // имя пользователя
                .claim("roles", roles)         // роли пользователя
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
