package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
    Optional<UserJpa> findByEmail(String email);
}
