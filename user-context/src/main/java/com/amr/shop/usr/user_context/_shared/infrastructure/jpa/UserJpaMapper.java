package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.usr.user_context.user.domain.UserModel;

public class UserJpaMapper {
  public static UserJpa toJpa(UserModel userModel) {
    UserToJpaMapper mapper = new UserToJpaMapper();
    userModel.accept(mapper);
    return mapper.getResult();
  }
}
