package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator.UserAdministratorJpa;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.customer.UserCustomerJpa;
import com.amr.shop.usr.user_context.user.domain.UserModel;
import com.amr.shop.usr.user_context.user.domain.administrator.UserAdministratorModel;
import com.amr.shop.usr.user_context.user.domain.customer.UserCustomerModel;
import org.springframework.stereotype.Component;

@Component
public class UserJpaToDomainMapper implements IUserJpaVisitor {
  private UserModel result;

  public static UserModel toDomain(UserJpa userJpa) {
    UserJpaToDomainMapper mapper = new UserJpaToDomainMapper();
    userJpa.accept(mapper);
    return mapper.getResult();
  }

  @Override
  public void visit(UserAdministratorJpa user) {
    result =
        new UserAdministratorModel(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getStatus(),
            user.getPhone(),
            user.getCreatedByAdminId());
  }

  @Override
  public void visit(UserCustomerJpa user) {
    result =
        new UserCustomerModel(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getStatus(),
            user.getPhone(),
            user.getAddress());
  }

  public UserModel getResult() {
    return result;
  }
}
