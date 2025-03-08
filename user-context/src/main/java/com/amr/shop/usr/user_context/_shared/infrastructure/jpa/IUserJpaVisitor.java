package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator.UserAdministratorJpa;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.customer.UserCustomerJpa;

public interface IUserJpaVisitor {
  void visit(UserAdministratorJpa user);

  void visit(UserCustomerJpa user);
}
