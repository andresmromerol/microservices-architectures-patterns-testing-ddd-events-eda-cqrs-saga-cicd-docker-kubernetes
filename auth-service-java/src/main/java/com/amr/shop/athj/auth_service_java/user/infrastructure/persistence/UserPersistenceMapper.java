package com.amr.shop.athj.auth_service_java.user.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpa modelToJpa(UserModel userModel) {
        return UserJpa.builder()
                .id(userModel.getId().getValue())
                .name(userModel.getName().getValue())
                .email(userModel.getEmail().getValue())
                .password(userModel.getPassword().getValue())
                .roles(userModel.getRoles())
                .status(userModel.getStatus().getValue())
                .phone(userModel.getPhone().getValue())
                .build();
    }

    public UserModel jpaToModel(UserJpa userJpa) {
        return UserModel.create(
                userJpa.getId(),
                userJpa.getName(),
                userJpa.getEmail(),
                userJpa.getPassword(),
                userJpa.getStatus(),
                userJpa.getPhone(),
                userJpa.getRoles());
    }
}
