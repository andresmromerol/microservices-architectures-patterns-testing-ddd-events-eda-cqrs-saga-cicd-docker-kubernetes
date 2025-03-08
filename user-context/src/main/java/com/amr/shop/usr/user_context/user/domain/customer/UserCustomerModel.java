package com.amr.shop.usr.user_context.user.domain.customer;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.usr.user_context.user.domain.IUserVisitor;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.UserModelDto;
import java.util.UUID;

public class UserCustomerModel extends UserModel {
  private final String address;

  public UserCustomerModel(
      UUID id, String name, String email, UserStatusEnum status, String phone, String address) {
    super(id, name, email, status, phone);
    this.address = address;
  }

  public static UserModel create(
      UUID id, String name, String email, UserStatusEnum status, String phone, String address) {
    return new UserCustomerModel(id, name, email, status, phone, address);
  }

  @Override
  public void accept(IUserVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public UserModelDto toPrimitives() {
    return new UserCustomerModelDto(
        this.getAggId(),
        this.getName(),
        this.getEmail(),
        this.getStatus(),
        this.getPhone(),
        this.getAddress());
  }

  @Override
  public boolean isNullModel() {
    return false;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public UUID getAggId() {
    return super.getAggId();
  }
}
