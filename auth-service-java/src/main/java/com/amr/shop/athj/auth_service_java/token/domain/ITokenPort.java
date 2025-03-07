package com.amr.shop.athj.auth_service_java.token.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;

public interface ITokenPort {
    String generateToken(BuildTokenDto token, String secretKey);

    String generateRefreshToken(BuildTokenDto token, String secretKey);

    String getSignInKey(String secretKey);
}
