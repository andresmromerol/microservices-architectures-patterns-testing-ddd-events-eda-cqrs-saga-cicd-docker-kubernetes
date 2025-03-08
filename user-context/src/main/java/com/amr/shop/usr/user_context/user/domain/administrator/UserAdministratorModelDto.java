package com.amr.shop.usr.user_context.user.domain.administrator;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.usr.user_context.user.domain.UserModelDto;
import java.util.UUID;

public class UserAdministratorModelDto extends UserModelDto {
  private UUID createdByAdminId;

  public UserAdministratorModelDto(
      UUID id,
      String name,
      String email,
      UserStatusEnum status,
      String phone,
      UUID createdByAdminId) {
    super(id, name, email, status, phone);
    this.createdByAdminId = createdByAdminId;
  }

  public UUID getCreatedByAdminId() {
    return createdByAdminId;
  }
}
