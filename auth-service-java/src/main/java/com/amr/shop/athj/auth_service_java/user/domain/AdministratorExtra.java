package com.amr.shop.athj.auth_service_java.user.domain;

import java.util.UUID;

public class AdministratorExtra {
  private final UUID createdByAdminId;

  public AdministratorExtra(UUID createdByAdminId) {
    this.createdByAdminId = createdByAdminId;
  }

  public UUID getCreatedByAdminId() {
    return createdByAdminId;
  }
}
