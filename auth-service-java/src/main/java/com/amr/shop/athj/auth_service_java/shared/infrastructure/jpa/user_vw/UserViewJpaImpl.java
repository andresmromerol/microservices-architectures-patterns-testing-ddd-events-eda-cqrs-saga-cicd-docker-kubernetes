package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user_vw;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserViewJpaImpl implements UserViewJpaDao {

    private final UserViewJpaRepository userViewJpaRepository;

    @Autowired
    public UserViewJpaImpl(UserViewJpaRepository userViewJpaRepository) {
        this.userViewJpaRepository = userViewJpaRepository;
    }

    @Override
    public Optional<UserJpa> findByEmail(String email) {
        return userViewJpaRepository.findByEmail(email);
    }
}
