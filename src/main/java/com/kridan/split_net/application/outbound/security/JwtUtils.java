package com.kridan.split_net.application.outbound.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private final Key key;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username, List<String> roles) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(username)                   // имя пользователя
                .setIssuer("self")                      // кто выдал токен
                .setIssuedAt(Date.from(now))            // время создания
                .setExpiration(Date.from(now.plusSeconds(86400))) // 1 день
                .claim("roles", roles)                  // роли
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
