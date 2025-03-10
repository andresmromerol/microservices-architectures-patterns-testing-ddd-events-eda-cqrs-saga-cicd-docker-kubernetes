package com.amr.shop.usr.user_context._shared.infrastructure.jpa;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data
@Table(name = "user_users")
public abstract class UserJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long auto;

  private UUID id;

  private String name;
  private String email;
  private UserStatusEnum status;
  private String phone;

  public abstract void accept(IUserJpaVisitor visitor);

  public abstract boolean isNullModel();
}
