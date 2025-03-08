package com.amr.shop.athj.auth_service_java.token.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RefreshTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken) {}
