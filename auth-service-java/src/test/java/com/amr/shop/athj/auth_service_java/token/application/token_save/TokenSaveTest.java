package com.amr.shop.athj.auth_service_java.token.application.token_save;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPersistencePort;
import com.amr.shop.athj.auth_service_java.token.domain.TokenModel;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenSaveTest {

  @Mock private ITokenPersistencePort tokenPersistencePort;

  private TokenSave tokenSave;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tokenSave = new TokenSave(tokenPersistencePort);
  }

  @Test
  void shouldSaveToken() {
    UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    String token = "test-token";
    doNothing().when(tokenPersistencePort).save(any(TokenModel.class));
    tokenSave.execute(userId, token);
    verify(tokenPersistencePort).save(any(TokenModel.class));
  }
}
