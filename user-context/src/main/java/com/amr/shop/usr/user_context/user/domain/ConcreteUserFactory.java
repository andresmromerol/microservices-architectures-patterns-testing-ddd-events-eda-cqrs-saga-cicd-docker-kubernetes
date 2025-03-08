package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModelDto;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModel;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModelDto;
import org.springframework.stereotype.Service;

@Service
public class ConcreteUserFactory implements IUserFactory {

  @Override
  public UserModel createUserAdministrator(UserAdministratorModelDto userDto) {
    return UserAdministratorModel.create(
        userDto.getId(),
        userDto.getName(),
        userDto.getEmail(),
        userDto.getStatus(),
        userDto.getPhone(),
        userDto.getCreatedByAdminId());
  }

  @Override
  public UserModel createUserCustomer(UserCustomerModelDto userCustomerModelDto) {
    return UserCustomerModel.create(
        userCustomerModelDto.getId(),
        userCustomerModelDto.getName(),
        userCustomerModelDto.getEmail(),
        userCustomerModelDto.getStatus(),
        userCustomerModelDto.getPhone(),
        userCustomerModelDto.getAddress());
  }
}
