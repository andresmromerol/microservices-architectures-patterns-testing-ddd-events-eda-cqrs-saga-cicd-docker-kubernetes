package com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email;

import com.amr.shop.athj.auth_service_java.user_vw.domain.IUserViewPersistencePort;
import com.amr.shop.athj.auth_service_java.user_vw.domain.UserView;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSearchByEmail {
  private final IUserViewPersistencePort userViewPersistencePort;

  @Autowired
  public UserSearchByEmail(IUserViewPersistencePort userViewPersistencePort) {
    this.userViewPersistencePort = userViewPersistencePort;
  }

  public UserSearchByEmailRes execute(String email) {
    log.info("Starting search user by email: {}", email);
    Optional<UserView> userReview = userViewPersistencePort.search(new EmailVo(email));
    if (userReview.isPresent()) {
      UserView model = userReview.get();
      log.info("User found: {}", model);
      return UserSearchByEmailRes.builder()
          .id(model.getId())
          .name(model.getName())
          .email(model.getEmail())
          .password(model.getPassword())
          .phone(model.getPhone())
          .roles(model.getRoles())
          .permissions(model.getPermissions())
          .isEmpty(false)
          .build();
    }
    log.info("User not found");
    return UserSearchByEmailRes.builder().isEmpty(true).build();
  }
}
