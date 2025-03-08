package com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateRequest {
  @NotBlank(message = "The email is required")
  @Email(message = "The email format is invalid")
  private String email;

  @NotBlank(message = "The password is required")
  @Size(min = 8, message = "The password must have at least 8 characters")
  private String password;
}
