package com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

  @NotBlank(message = "The current password is required")
  private String currentPassword;

  @NotBlank(message = "The new password is required")
  @Size(min = 8, message = "The new password must have at least 8 characters")
  private String newPassword;

  @NotBlank(message = "The confirmation password is required")
  @Size(min = 8, message = "The confirmation password must have at least 8 characters")
  private String confirmationPassword;
}
