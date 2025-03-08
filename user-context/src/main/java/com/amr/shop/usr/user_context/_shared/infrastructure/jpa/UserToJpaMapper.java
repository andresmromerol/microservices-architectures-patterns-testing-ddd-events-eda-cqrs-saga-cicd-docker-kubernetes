package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator.UserAdministratorJpa;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.customer.UserCustomerJpa;
import com.amr.shop.usr.user_context.user.domain.IUserVisitor;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModel;

public class UserToJpaMapper implements IUserVisitor {
  private UserJpa result;

  @Override
  public void visit(UserAdministratorModel user) {
    UserAdministratorJpa adminJpa = new UserAdministratorJpa();
    adminJpa.setId(user.getId().getValue());
    adminJpa.setName(user.getName());
    adminJpa.setEmail(user.getEmail());
    adminJpa.setStatus(user.getStatus());
    adminJpa.setPhone(user.getPhone());
    adminJpa.setCreatedByAdminId(user.getCreatedByAdminId());
    this.result = adminJpa;
  }

  @Override
  public void visit(UserCustomerModel user) {
    UserCustomerJpa customerJpa = new UserCustomerJpa();
    customerJpa.setId(user.getId().getValue());
    customerJpa.setName(user.getName());
    customerJpa.setEmail(user.getEmail());
    customerJpa.setStatus(user.getStatus());
    customerJpa.setPhone(user.getPhone());
    customerJpa.setAddress(user.getAddress());
    this.result = customerJpa;
  }

  public UserJpa getResult() {
    return result;
  }
}
