package com.amr.shop.usr.user_context._shared.infrastructure.jpa.customer;

import com.amr.shop.usr.user_context._shared.infrastructure.jpa.IUserJpaVisitor;
import com.amr.shop.usr.user_context._shared.infrastructure.jpa.UserJpa;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class UserCustomerJpa extends UserJpa {
  private String address;

  @Override
  public void accept(IUserJpaVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public boolean isNullModel() {
    return false;
  }
}
