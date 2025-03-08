package com.amr.shop.cmmj.common_java_context.services.auth;

import java.util.UUID;

public enum PermissionEnum {
  ADMIN_READ(UUID.fromString("cab69b41-878a-42ef-aeac-410aacff41ae"), "admin:read"),
  ADMIN_UPDATE(UUID.fromString("d320c72e-a3f8-4a7b-88cd-ad610d1e5412"), "admin:update"),
  ADMIN_CREATE(UUID.fromString("161e6826-106c-4225-98eb-124bea236db3"), "admin:create"),
  ADMIN_DELETE(UUID.fromString("5f619629-6623-42e6-96d9-0d581fdebb8b"), "admin:delete"),
  MANAGER_READ(UUID.fromString("7867bdb0-c63f-462e-a894-b0a9ed444933"), "management:read"),
  MANAGER_UPDATE(UUID.fromString("045ded93-4e5a-4dd8-81e3-fceaeafa4db0"), "management:update"),
  MANAGER_CREATE(UUID.fromString("465d5aa1-f8d0-419d-98ef-92996287e78e"), "management:create"),
  MANAGER_DELETE(UUID.fromString("c0b3973f-4328-4256-b6b0-5018da438ec3"), "management:delete");

  private final UUID id;
  private final String permission;

  PermissionEnum(UUID id, String permission) {
    this.id = id;
    this.permission = permission;
  }

  public UUID getId() {
    return id;
  }

  public String getPermission() {
    return permission;
  }

  public String getValue() {
    return id.toString();
  }
}
