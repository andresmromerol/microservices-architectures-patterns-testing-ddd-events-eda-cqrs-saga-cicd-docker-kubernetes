package com.amr.shop.athj.auth_service_java.token.application.token_generate;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenGenerateHdr implements IQueryHandler<TokenGenerateQry, TokenGenerateRes> {

  private final TokenGenerate tokenGenerate;

  @Autowired
  public TokenGenerateHdr(TokenGenerate tokenGenerate) {
    this.tokenGenerate = tokenGenerate;
  }

  @Override
  public TokenGenerateRes handle(TokenGenerateQry query) {
    return tokenGenerate.execute(query.token(), query.secretKey());
  }
}
