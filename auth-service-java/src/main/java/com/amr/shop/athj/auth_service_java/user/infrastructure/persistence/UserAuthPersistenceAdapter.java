package com.amr.shop.athj.auth_service_java.user.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpaRepository;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthModel;
import com.amr.shop.athj.auth_service_java.user.domain.exception.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IUserAuthPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthPersistenceAdapter implements IUserAuthPersistencePort {

  private final UserJpaRepository userJpaRepository;
  private final UserPersistenceMapper userPersistenceMapper;

  @Autowired
  public UserAuthPersistenceAdapter(
      UserJpaRepository userJpaRepository, UserPersistenceMapper userPersistenceMapper) {
    this.userJpaRepository = userJpaRepository;
    this.userPersistenceMapper = userPersistenceMapper;
  }

  @Override
  public void save(UserAuthModel userAuthModel) {
    log.info("Starting saving user with id: {}", userAuthModel.getId().getValue());
    userJpaRepository.save(userPersistenceMapper.modelToJpa(userAuthModel));
    log.info("User saved successfully with id: {}", userAuthModel.getId().getValue());
  }

  @Override
  public void update(UserAuthModel userAuthModel) {
    UserJpa user =
        userJpaRepository
            .findByEmail(userAuthModel.getEmail().getValue())
            .orElseThrow(
                () -> new UserAuthUserNotFoundException(userAuthModel.getEmail().getValue()));
    user.setName(userAuthModel.getName().getValue());
    user.setEmail(userAuthModel.getEmail().getValue());
    user.setPhone(userAuthModel.getPhone().getValue());
    user.setPassword(userAuthModel.getPassword().getValue());
    user.setRoles(userAuthModel.getRoles());
    user.setStatus(userAuthModel.getStatus().getValue());
    userJpaRepository.save(user);
  }
}
