package com.kridan.split_net.application.outbound.db.user;

import com.kridan.split_net.domain.user.LocalCredentials;
import com.kridan.split_net.domain.user.User;
import com.kridan.split_net.domain.user.UserIdentity;
import com.kridan.split_net.domain.user.ports.SaveUserPort;
import com.kridan.split_net.infrastructure.database.repository.user.LocalCredentialsRepository;
import com.kridan.split_net.infrastructure.database.repository.user.UserIdentityRepository;
import com.kridan.split_net.infrastructure.database.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveUserAdapter implements SaveUserPort {

    private final UserRepository userRepository;
    private final UserIdentityRepository userIdentityRepository;
    private final LocalCredentialsRepository localCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(User user, String password) {


        User savedUser = userRepository.save(user);

        UserIdentity userIdentity = new UserIdentity()
                .setUser(savedUser)
                .setProvider("LOCAL")
                .setProviderId(savedUser.getEmail());
        UserIdentity savedUserIdentity = userIdentityRepository.save(userIdentity);

        LocalCredentials localCredentials = new LocalCredentials()
                .setIdentity(savedUserIdentity)
                .setPasswordHash(passwordEncoder.encode(password));
        localCredentialsRepository.save(localCredentials);

        return savedUser;
    }
}
