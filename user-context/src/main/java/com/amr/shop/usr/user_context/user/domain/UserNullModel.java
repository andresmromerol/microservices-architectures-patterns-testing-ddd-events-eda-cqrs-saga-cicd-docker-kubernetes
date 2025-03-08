package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import java.util.UUID;

public class UserNullModel extends UserModel {
  public UserNullModel() {
    super(
        UUID.fromString("28c624d2-133f-46c3-8d7b-03e5f3e77a4e"),
        "null",
        "null@mail.com",
        UserStatusEnum.NULL_OBJECT,
        "0000000000");
  }

  @Override
  public void accept(IUserVisitor visitor) {}

  @Override
  public UserModelDto toPrimitives() {
    return null;
  }

  @Override
  public boolean isNullModel() {
    return true;
  }
}
