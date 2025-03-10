package com.amr.shop.usr.user_context.user.application.search_by_id;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.usr.user_context.user.domain.IUserPersistencePort;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.UserNullModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSearchById {
  private final IUserPersistencePort userPersistencePort;

  @Autowired
  public UserSearchById(IUserPersistencePort userPersistencePort) {
    this.userPersistencePort = userPersistencePort;
  }

  public UserSearchByEmailRes execute(String email, RoleEnum role) {
    UserModel user = getUser(email, role);
    return new UserSearchByEmailRes(
        user.getId().getValue(),
        user.getName(),
        user.getEmail(),
        user.getStatus(),
        user.getPhone(),
        user.isNullModel());
  }

  private UserModel getUser(String email, RoleEnum role) {
    return userPersistencePort
        .findByEmailAndRole(new EmailVo(email), role)
        .orElse(new UserNullModel());
  }
}
