package com.kridan.split_net.application.outbound.security;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private final FindUserPort findUserPort;

    private final Key key;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret, FindUserPort findUserPort) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.findUserPort = findUserPort;
    }

    public String generateToken(String email, List<String> roles) {
        Instant now = Instant.now();

        User user = findUserPort.findByEmail(email);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer("self")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(86400)))
                .claim("email", email)
                .claim("roles", roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
