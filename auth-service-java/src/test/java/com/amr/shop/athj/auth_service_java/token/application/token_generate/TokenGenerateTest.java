package com.amr.shop.athj.auth_service_java.token.application.token_generate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenGenerateTest {

  private static final String VALID_EMAIL = "andres@email.com";
  private static final String VALID_SECRET_KEY = "test-secret-key";
  private static final long VALID_EXPIRATION = 1000L;
  private static final String EXPECTED_TOKEN = "generated-token";

  @Mock private ITokenPort tokenPort;

  private TokenGenerate tokenGenerate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tokenGenerate = new TokenGenerate(tokenPort);
  }

  @Test
  void shouldGenerateToken() {
    Map<String, Object> extraClaims = new HashMap<>();
    UUID userId = UUID.randomUUID();
    extraClaims.put("id", userId);
    extraClaims.put("role", "USER");
    extraClaims.put("email", VALID_EMAIL);
    BuildTokenDto tokenDto = new BuildTokenDto(VALID_EMAIL, VALID_EXPIRATION, extraClaims);
    when(tokenPort.generateToken(tokenDto, VALID_SECRET_KEY)).thenReturn(EXPECTED_TOKEN);
    TokenGenerateRes response = tokenGenerate.execute(tokenDto, VALID_SECRET_KEY);
    assertNotNull(response);
    assertEquals(EXPECTED_TOKEN, response.tokenGenerated());
    verify(tokenPort, times(1)).generateToken(tokenDto, VALID_SECRET_KEY);
  }
}
