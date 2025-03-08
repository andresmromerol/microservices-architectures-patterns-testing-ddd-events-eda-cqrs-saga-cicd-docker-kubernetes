package com.amr.shop.athj.auth_service_java.token.application.token_refresh;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenRefresh {
  private final ITokenPort tokenPort;

  @Autowired
  public TokenRefresh(ITokenPort tokenPort) {
    this.tokenPort = tokenPort;
  }

  public TokenRefreshRes execute(BuildTokenDto token, String secretKey) {
    log.info("refreshing token for user: {}", token.getUsername());
    String refreshToken = tokenPort.generateRefreshToken(token, secretKey);
    log.info("refreshed token for user: {}", token.getUsername());
    return new TokenRefreshRes(refreshToken);
  }
}
