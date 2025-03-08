package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModel;

public interface IUserVisitor {
  void visit(UserAdministratorModel user);

  void visit(UserCustomerModel user);
}
