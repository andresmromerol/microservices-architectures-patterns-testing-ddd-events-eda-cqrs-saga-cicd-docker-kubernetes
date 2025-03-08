package com.amr.shop.usr.user_context.user.domain.customer;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.usr.user_context.user.domain.UserModelDto;
import java.util.UUID;

public class UserCustomerModelDto extends UserModelDto {
  private String address;

  public UserCustomerModelDto(
      UUID id, String name, String email, UserStatusEnum status, String phone, String address) {
    super(id, name, email, status, phone);
    this.address = address;
  }

  public String getAddress() {
    return address;
  }
}
