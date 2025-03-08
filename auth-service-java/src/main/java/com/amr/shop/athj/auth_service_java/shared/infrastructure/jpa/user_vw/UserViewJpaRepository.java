package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user_vw;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserViewJpaRepository extends JpaRepository<UserJpa, UUID> {

  Optional<UserJpa> findByEmail(String email);
}
