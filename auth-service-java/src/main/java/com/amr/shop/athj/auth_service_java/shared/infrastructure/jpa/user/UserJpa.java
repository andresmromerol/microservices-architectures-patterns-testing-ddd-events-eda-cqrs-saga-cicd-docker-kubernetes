package com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpa;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import jakarta.persistence.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_users")
public class UserJpa implements UserDetails {

  @Id private UUID id;

  private String name;
  private String email;
  private String phone;
  private String password;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<RoleEnum> roles = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private Set<TokenJpa> tokens = new HashSet<>();

  @Enumerated(EnumType.STRING)
  private UserStatusEnum status;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
