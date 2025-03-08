package com.amr.shop.cmmj.common_java_context.services.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BuildTokenDto {
  private UUID userId;
  private String name;
  private String username;
  private Set<RoleEnum> roles;
  private Map<String, Object> extraClaims;
  private long expiration;

  public BuildTokenDto(String username, long expiration, Map<String, Object> claims) {
    this.username = username;
    this.expiration = expiration;
    this.extraClaims = (Map<String, Object>) (claims != null ? claims : new HashMap());
  }

  public BuildTokenDto(
      UUID userId, String name, String username, Set<RoleEnum> roles, long expiration) {
    this.userId = userId;
    this.name = name;
    this.username = username;
    this.roles = roles;
    this.expiration = expiration;
    this.extraClaims = new HashMap<>();
  }

  public UUID getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public Set<RoleEnum> getRoles() {
    return roles;
  }

  public Map<String, Object> getExtraClaims() {
    return extraClaims;
  }

  public long getExpiration() {
    return expiration;
  }

  public Map<String, Object> buildResourceAccess() {
    Map<String, Object> resourceAccess = new HashMap<>();
    Set<RoleEnum> roleEnums =
        roles.stream().map(role -> RoleEnum.fromName(role.name())).collect(Collectors.toSet());
    resourceAccess.put("roles", RoleEnum.getRoleValues(roleEnums));
    return resourceAccess;
  }

  public Map<String, Object> buildClaims() {
    Map<String, Object> claims = new HashMap<>(extraClaims);
    claims.put("id", userId);
    claims.put("name", name);
    claims.put("resource_access", buildResourceAccess());
    return claims;
  }
}
