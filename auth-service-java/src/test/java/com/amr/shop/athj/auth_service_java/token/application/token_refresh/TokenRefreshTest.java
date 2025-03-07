package com.amr.shop.athj.auth_service_java.token.application.token_refresh;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenRefreshTest {

    private static final String VALID_EMAIL = "andres@email.com";
    private static final String VALID_SECRET_KEY = "test-secret-key";
    private static final long VALID_EXPIRATION = 1000L;
    private static final String EXPECTED_REFRESH_TOKEN = "refresh-token";

    @Mock
    private ITokenPort tokenPort;

    private TokenRefresh tokenRefresh;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenRefresh = new TokenRefresh(tokenPort);
    }

    @Test
    void shouldRefreshToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "USER");
        BuildTokenDto tokenDto = new BuildTokenDto(VALID_EMAIL, VALID_EXPIRATION, extraClaims);

        when(tokenPort.generateRefreshToken(tokenDto, VALID_SECRET_KEY)).thenReturn(EXPECTED_REFRESH_TOKEN);

        TokenRefreshRes response = tokenRefresh.execute(tokenDto, VALID_SECRET_KEY);

        assertNotNull(response);
        assertEquals(EXPECTED_REFRESH_TOKEN, response.refreshTokenGenerated());
        verify(tokenPort, times(1)).generateRefreshToken(tokenDto, VALID_SECRET_KEY);
    }
}
