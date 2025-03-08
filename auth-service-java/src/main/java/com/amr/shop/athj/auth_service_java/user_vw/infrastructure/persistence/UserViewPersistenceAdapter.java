package com.amr.shop.athj.auth_service_java.user_vw.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user_vw.UserViewJpaRepository;
import com.amr.shop.athj.auth_service_java.user_vw.domain.IUserViewPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.domain.UserView;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserViewPersistenceAdapter implements IUserViewPersistencePort {

  private final UserViewJpaRepository userViewJpaRepository;
  private final UserViewPersistenceMapper userViewPersistenceMapper;

  @Autowired
  public UserViewPersistenceAdapter(
      UserViewJpaRepository userViewJpaRepository,
      UserViewPersistenceMapper userViewPersistenceMapper) {
    log.info("Initializing UserView persistence adapter");
    this.userViewJpaRepository = userViewJpaRepository;
    this.userViewPersistenceMapper = userViewPersistenceMapper;
  }

  @Override
  public Optional<UserView> search(EmailVo email) {
    log.info("Searching UserView by email: {}", email.getValue());
    return userViewJpaRepository
        .findByEmail(email.getValue())
        .map(userViewPersistenceMapper::jpaToModel);
  }
}
