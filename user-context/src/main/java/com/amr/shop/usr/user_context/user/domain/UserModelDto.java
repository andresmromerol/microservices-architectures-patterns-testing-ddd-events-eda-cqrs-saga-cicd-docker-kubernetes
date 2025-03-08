package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import java.util.UUID;

public abstract class UserModelDto {
  private final UUID id;
  private final String name;
  private final String email;
  private final UserStatusEnum status;
  private final String phone;

  public UserModelDto(UUID id, String name, String email, UserStatusEnum status, String phone) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
    this.phone = phone;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public UserStatusEnum getStatus() {
    return status;
  }

  public String getPhone() {
    return phone;
  }
}
