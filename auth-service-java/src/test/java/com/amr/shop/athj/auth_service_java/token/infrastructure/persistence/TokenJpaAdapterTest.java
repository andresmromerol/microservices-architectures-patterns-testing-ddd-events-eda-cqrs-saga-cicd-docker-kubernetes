package com.amr.shop.athj.auth_service_java.token.infrastructure.persistence;

import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.ITokenJpaDao;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpa;
import com.amr.shop.athj.auth_service_java.token.domain.TokenModel;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenId;
import com.amr.shop.cmmj.common_java_context.services.auth.TokenType;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenJpaAdapterTest {

  @Mock private ITokenJpaDao tokenJpaDao;

  @Mock private TokenJpaMapper tokenJpaMapper;

  private TokenJpaAdapter tokenJpaAdapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tokenJpaAdapter = new TokenJpaAdapter(tokenJpaDao, tokenJpaMapper);
  }

  @Test
  void shouldRevokeUserTokens() {
    UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    UserId userIdVo = new UserId(userId);
    Set<TokenJpa> userTokens = new HashSet<>();
    when(tokenJpaDao.findByUserAndValid(userIdVo)).thenReturn(userTokens);
    doNothing().when(tokenJpaDao).markAsRevokedAndExpiredTokens(userTokens);
    doNothing().when(tokenJpaDao).saveRevokedTokens(userTokens);
    tokenJpaAdapter.revokeUserTokens(userIdVo);
    verify(tokenJpaDao).findByUserAndValid(userIdVo);
    verify(tokenJpaDao).markAsRevokedAndExpiredTokens(userTokens);
    verify(tokenJpaDao).saveRevokedTokens(userTokens);
  }

  @Test
  void shouldSaveToken() {
    TokenModel tokenModel =
        TokenModel.create(
            new TokenId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")),
            "test-token",
            TokenType.BEARER,
            false,
            false,
            new UserId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")));
    TokenJpa tokenJpa = new TokenJpa();
    when(tokenJpaMapper.toTokenJpa(tokenModel)).thenReturn(tokenJpa);
    doNothing().when(tokenJpaDao).save(tokenJpa);
    tokenJpaAdapter.save(tokenModel);
    verify(tokenJpaMapper).toTokenJpa(tokenModel);
    verify(tokenJpaDao).save(tokenJpa);
  }
}
