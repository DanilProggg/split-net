package com.kridan.split_net.application.outbound.db;

import com.kridan.split_net.domain.model.User;
import com.kridan.split_net.domain.ports.outbound.db.SaveUserPort;
import com.kridan.split_net.infrastructure.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveUserAdapter implements SaveUserPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {


        //Encode password
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}
