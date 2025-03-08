package com.amr.shop.athj.auth_service_java.user.infrastructure.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserAuthResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken) {}
