package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user_vw;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import java.util.Optional;

public interface UserViewJpaDao {
    Optional<UserJpa> findByEmail(String email);
}
