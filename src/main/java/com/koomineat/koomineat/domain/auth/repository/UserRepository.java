package com.koomineat.koomineat.domain.auth.repository;

import com.koomineat.koomineat.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthToken(String authToken);
}
