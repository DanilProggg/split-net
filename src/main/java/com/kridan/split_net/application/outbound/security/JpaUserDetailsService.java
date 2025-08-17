package com.kridan.split_net.application.outbound.security;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final FindUserPort findUserPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserPort.findByUsername(username);

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()) // пароль уже закодирован (BCrypt)
                .roles("USER") // или подтягиваешь из user.roles
                .build();
    }

}
