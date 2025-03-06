package com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateRequest {
    private String email;
    private String password;
}
