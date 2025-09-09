package com.kridan.split_net.application.outbound.security;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.model.UserIdentity;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final FindUserPort findUserPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserPort.findByEmail(username);
        UserIdentity userIdentity = user.getIdentities().stream()
                .filter(userIdentity1 -> userIdentity1.getProvider().equals("LOCAL"))
                .findFirst()
                .orElseThrow(()->new RuntimeException("User identity not found"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(userIdentity.getLocalCredentials().getPasswordHash()) // пароль уже закодирован (BCrypt)
                .roles("USER") // или подтягиваешь из user.roles
                .build();
    }

}
