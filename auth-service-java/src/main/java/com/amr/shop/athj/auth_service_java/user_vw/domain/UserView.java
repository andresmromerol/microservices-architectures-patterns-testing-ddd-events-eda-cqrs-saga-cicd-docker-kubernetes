package com.amr.shop.athj.auth_service_java.user_vw.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import java.util.Set;
import java.util.UUID;

public class UserView {
  private UUID id;
  private String name;
  private String email;
  private String password;
  private String phone;
  private Set<RoleEnum> roles;
  private Set<PermissionEnum> permissions;

  public UserView(
      UUID id,
      String name,
      String email,
      String password,
      String phone,
      Set<RoleEnum> roles,
      Set<PermissionEnum> permissions) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.roles = roles;
    this.permissions = permissions;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public Set<RoleEnum> getRoles() {
    return roles;
  }

  public Set<PermissionEnum> getPermissions() {
    return permissions;
  }
}
