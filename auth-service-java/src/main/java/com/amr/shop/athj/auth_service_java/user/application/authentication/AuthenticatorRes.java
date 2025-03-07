package com.amr.shop.athj.auth_service_java.user.application.authentication;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;

public record AuthenticatorRes(String accessToken, String refreshToken) implements IResponse {}
