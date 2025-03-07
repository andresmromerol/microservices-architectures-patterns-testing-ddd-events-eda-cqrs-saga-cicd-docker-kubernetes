package com.amr.shop.athj.auth_service_java.token.application.token_refresh;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRefreshHdr implements IQueryHandler<TokenRefreshQry, TokenRefreshRes> {
    private final TokenRefresh tokenRefresh;

    @Autowired
    public TokenRefreshHdr(TokenRefresh tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    @Override
    public TokenRefreshRes handle(TokenRefreshQry query) {
        return tokenRefresh.execute(query.token(), query.secretKey());
    }
}
