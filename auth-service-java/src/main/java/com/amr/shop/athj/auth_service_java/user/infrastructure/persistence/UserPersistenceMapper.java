package com.amr.shop.athj.auth_service_java.user.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthModel;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

  public UserJpa modelToJpa(UserAuthModel userAuthModel) {
    return UserJpa.builder()
        .id(userAuthModel.getId().getValue())
        .name(userAuthModel.getName().getValue())
        .email(userAuthModel.getEmail().getValue())
        .password(userAuthModel.getPassword().getValue())
        .roles(userAuthModel.getRoles())
        .status(userAuthModel.getStatus().getValue())
        .phone(userAuthModel.getPhone().getValue())
        .build();
  }

  public UserAuthModel jpaToModel(UserJpa userJpa) {
    return UserAuthModel.create(
        userJpa.getId(),
        userJpa.getName(),
        userJpa.getEmail(),
        userJpa.getPassword(),
        userJpa.getStatus(),
        userJpa.getPhone(),
        userJpa.getRoles(),
        Set.of());
  }
}
