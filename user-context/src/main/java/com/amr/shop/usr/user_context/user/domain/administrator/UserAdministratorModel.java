package com.amr.shop.usr.user_context.user.domain.administrator;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.usr.user_context.user.domain.IUserVisitor;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.UserModelDto;
import java.util.UUID;

public class UserAdministratorModel extends UserModel {
  private final UUID createdByAdminId;

  public UserAdministratorModel(
      UUID id,
      String name,
      String email,
      UserStatusEnum status,
      String phone,
      UUID createdByAdminId) {
    super(id, name, email, status, phone);
    this.createdByAdminId = createdByAdminId;
  }

  public static UserAdministratorModel create(
      UUID id,
      String name,
      String email,
      UserStatusEnum status,
      String phone,
      UUID createdByAdminId) {
    return new UserAdministratorModel(id, name, email, status, phone, createdByAdminId);
  }

  @Override
  public void accept(IUserVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public UserModelDto toPrimitives() {
    return new UserAdministratorModelDto(
        this.getAggId(),
        this.getName(),
        this.getEmail(),
        this.getStatus(),
        this.getPhone(),
        this.getCreatedByAdminId());
  }

  @Override
  public boolean isNullModel() {
    return false;
  }

  public UUID getCreatedByAdminId() {
    return createdByAdminId;
  }
}
