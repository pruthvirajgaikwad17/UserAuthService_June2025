package com.example.userauthservice_june2025.repos;

import com.example.userauthservice_june2025.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User save(User user);

    @Override
    Optional<User> findById(Long id);
}
