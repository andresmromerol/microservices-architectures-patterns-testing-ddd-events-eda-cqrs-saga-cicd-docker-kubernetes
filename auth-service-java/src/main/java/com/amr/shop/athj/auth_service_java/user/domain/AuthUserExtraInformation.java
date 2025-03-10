package com.amr.shop.athj.auth_service_java.user.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;

public class AuthUserExtraInformation {
  private final RoleEnum role;
  private AdministratorExtra administratorExtra;
  private CustomerExtra customerExtra;

  public AuthUserExtraInformation(AdministratorExtra administratorExtra, RoleEnum role) {
    this.administratorExtra = administratorExtra;
    this.role = role;
  }

  public AuthUserExtraInformation(CustomerExtra customerExtra, RoleEnum role) {
    this.customerExtra = customerExtra;
    this.role = role;
  }

  public RoleEnum getRole() {
    return role;
  }

  public AdministratorExtra getAdministratorExtra() {
    return administratorExtra;
  }

  public CustomerExtra getCustomerExtra() {
    return customerExtra;
  }
}
