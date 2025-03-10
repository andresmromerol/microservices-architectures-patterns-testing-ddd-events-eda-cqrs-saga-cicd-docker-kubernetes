package com.amr.shop.athj.auth_service_java.user.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.event.AdministratorRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.services.auth.event.CustomerRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.services.user.vo.*;
import com.amr.shop.cmmj.common_java_context.shared.abstracts.AggregateRoot;
import java.util.Set;
import java.util.UUID;

public class UserAuthModel extends AggregateRoot<UserId> {

  private final NameVo name;
  private final EmailVo email;
  private final PasswordVo password;
  private final UserStatus status;
  private final PhoneVo phone;
  private final Set<RoleEnum> roles;
  private Set<AuthUserExtraInformation> extraInformation;

  public UserAuthModel(
      UserId authId,
      NameVo name,
      EmailVo email,
      PasswordVo password,
      UserStatus status,
      PhoneVo phone,
      Set<RoleEnum> roles) {
    super.setId(authId);
    this.name = name;
    this.email = email;
    this.password = password;
    this.status = status;
    this.phone = phone;
    this.roles = roles;
  }

  public static UserAuthModel create(
      UUID id,
      String name,
      String email,
      String password,
      UserStatusEnum status,
      String phone,
      Set<RoleEnum> roles,
      Set<AuthUserExtraInformation> extraInformation) {

    UserAuthModel user =
        new UserAuthModel(
            new UserId(id),
            new NameVo(name),
            new EmailVo(email),
            new PasswordVo(password),
            new UserStatus(status),
            new PhoneVo(phone),
            roles);

    if (extraInformation != null && !extraInformation.isEmpty()) {
      for (AuthUserExtraInformation information : extraInformation) {
        if (information.getRole().equals(RoleEnum.ADMIN)) {
          user.record(
              new AdministratorRegisteredEvent(
                  id.toString(),
                  name,
                  email,
                  information.getAdministratorExtra().getCreatedByAdminId().toString(),
                  phone));
        }
        if (information.getRole().equals(RoleEnum.CUSTOMER)) {
          user.record(
              new CustomerRegisteredEvent(
                  id.toString(), name, email, information.getCustomerExtra().getAddress(), phone));
        }
      }
    }
    return user;
  }

  public static UserAuthModel update(
      UUID id,
      String name,
      String email,
      String password,
      UserStatusEnum status,
      String phone,
      Set<RoleEnum> roles) {

    return new UserAuthModel(
        new UserId(id),
        new NameVo(name),
        new EmailVo(email),
        new PasswordVo(password),
        new UserStatus(status),
        new PhoneVo(phone),
        roles);
  }

  public NameVo getName() {
    return name;
  }

  public EmailVo getEmail() {
    return email;
  }

  public PasswordVo getPassword() {
    return password;
  }

  public UserStatus getStatus() {
    return status;
  }

  public PhoneVo getPhone() {
    return phone;
  }

  public Set<RoleEnum> getRoles() {
    return roles;
  }
}
