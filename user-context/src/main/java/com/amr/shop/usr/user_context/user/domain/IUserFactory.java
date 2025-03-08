package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModelDto;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModelDto;

public interface IUserFactory {
  UserModel createUserAdministrator(UserAdministratorModelDto userDto);

  UserModel createUserCustomer(UserCustomerModelDto userCustomerModelDto);
}
