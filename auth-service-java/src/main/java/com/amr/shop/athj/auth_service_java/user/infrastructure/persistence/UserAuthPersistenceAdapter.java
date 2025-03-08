package com.amr.shop.athj.auth_service_java.user.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpaRepository;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.UserModel;
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
  public void save(UserModel userModel) {
    log.info("Starting saving user with id: {}", userModel.getId().getValue());
    userJpaRepository.save(userPersistenceMapper.modelToJpa(userModel));
    log.info("User saved successfully with id: {}", userModel.getId().getValue());
  }

  @Override
  public void update(UserModel userModel) {
    UserJpa user =
        userJpaRepository
            .findByEmail(userModel.getEmail().getValue())
            .orElseThrow(() -> new UserAuthUserNotFoundException(userModel.getEmail().getValue()));
    user.setName(userModel.getName().getValue());
    user.setEmail(userModel.getEmail().getValue());
    user.setPhone(userModel.getPhone().getValue());
    user.setPassword(userModel.getPassword().getValue());
    user.setRoles(userModel.getRoles());
    user.setStatus(userModel.getStatus().getValue());
    userJpaRepository.save(user);
  }
}
