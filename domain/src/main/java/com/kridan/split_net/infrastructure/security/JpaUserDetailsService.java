package com.kridan.split_net.infrastructure.security;

import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.UserIdentity;
import com.kridan.split_net.domain.user.ports.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService {

    private final FindUserPort findUserPort;
    private final PasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserPort.findByEmail(username);
        UserIdentity userIdentity = user.getIdentities().stream()
                .filter(userIdentity1 -> userIdentity1.getProvider().equals("LOCAL"))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User identity not found"));

        return user;
    }

    public boolean auth(String username, String password) {
        User user = findUserPort.findByEmail(username);
        UserIdentity userIdentity = user.getIdentities().stream()
                .filter(userIdentity1 -> userIdentity1.getProvider().equals("LOCAL"))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User identity not found"));
        return passwordEncoder.matches(password, userIdentity.getLocalCredentials().getPasswordHash());
    }

}
