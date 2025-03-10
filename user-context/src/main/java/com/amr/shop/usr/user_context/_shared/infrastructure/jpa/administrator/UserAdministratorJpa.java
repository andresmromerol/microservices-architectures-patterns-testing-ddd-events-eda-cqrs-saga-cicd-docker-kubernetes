package com.amr.shop.usr.user_context._shared.infrastructure.jpa.administrator;

import com.amr.shop.usr.user_context._shared.infrastructure.jpa.IUserJpaVisitor;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.UserJpa;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class UserAdministratorJpa extends UserJpa {
  private UUID createdByAdminId;

  @Override
  public void accept(IUserJpaVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public boolean isNullModel() {
    return false;
  }
}
