package com.amr.shop.athj.auth_service_java.token.application.token_refresh;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;

public record TokenRefreshRes(String refreshTokenGenerated) implements IResponse {}
