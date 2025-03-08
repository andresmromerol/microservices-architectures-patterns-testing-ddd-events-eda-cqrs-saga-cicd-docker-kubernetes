package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenType;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class TokenJpa {

  @Id public UUID id;

  @Column(unique = true, length = 2000)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public UserJpa user;
}
