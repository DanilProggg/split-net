package com.kridan.split_net.infrastructure.database.repository.user;

import com.kridan.split_net.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u FROM User u
        WHERE (:emailPart IS NULL OR :emailPart = '' OR u.email LIKE CONCAT('%', :emailPart, '%'))
        ORDER BY u.email ASC
    """)
    Page<User> searchByEmail(@Param("emailPart") String emailPart, Pageable pageable);
}
