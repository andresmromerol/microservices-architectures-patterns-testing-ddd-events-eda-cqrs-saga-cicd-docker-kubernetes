package com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRegisterRequest {
  @NotBlank(message = "The name is required")
  @Size(min = 3, max = 50, message = "The name must have between 3 and 50 characters")
  private String name;

  @NotBlank(message = "The email is required")
  @Email(message = "The email format is invalid")
  private String email;

  @NotBlank(message = "The phone number is required")
  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "The phone number format is invalid")
  private String phone;

  @NotBlank(message = "The password is required")
  @Size(min = 8, message = "The password must have at least 8 characters")
  private String password;

  @JsonProperty("role")
  private Set<UUID> roleUuids = new HashSet<>();

  public Set<RoleEnum> getRoles() {
    return RoleEnum.fromUUIDs(roleUuids);
  }
}
