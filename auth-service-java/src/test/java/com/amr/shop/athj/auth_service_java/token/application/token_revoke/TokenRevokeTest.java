package com.amr.shop.athj.auth_service_java.token.application.token_revoke;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPersistencePort;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenRevokeTest {

    @Mock
    private ITokenPersistencePort tokenRevokePort;

    private TokenRevoke tokenRevoke;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenRevoke = new TokenRevoke(tokenRevokePort);
    }

    @Test
    void shouldRevokeUserTokens() {
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UserId userIdVo = new UserId(userId);

        doNothing().when(tokenRevokePort).revokeUserTokens(userIdVo);

        tokenRevoke.execute(userId);

        verify(tokenRevokePort).revokeUserTokens(userIdVo);
    }
}
