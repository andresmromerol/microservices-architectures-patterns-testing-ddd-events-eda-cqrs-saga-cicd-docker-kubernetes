package com.amr.shop.athj.auth_service_java.token.infrastructure.token;

import static org.junit.jupiter.api.Assertions.*;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenAdapterTest {

    private static final long TOKEN_EXPIRATION = 3600000L;
    private static final UUID USER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final String USER_NAME = "andres";
    private static final String USER_EMAIL = "andres@email.com";
    private static final String TEST_SECRET_KEY = "6F7564526E4A586C6F336437754C35766B594E5138394650425378586C414F62";
    private static final UUID USER_ROLE_ID = UUID.fromString("5c2ced8b-28f9-4476-aa83-4f418088ea8d");

    private ITokenPort tokenAdapter;
    private String secretKey;

    @BeforeEach
    void setUp() {
        tokenAdapter = new TokenAdapter();
        secretKey = Base64.getEncoder().encodeToString(TEST_SECRET_KEY.getBytes());
    }

    @Test
    void shouldGenerateTokenWithValidClaims() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        BuildTokenDto tokenDto = new BuildTokenDto(USER_ID, USER_NAME, USER_EMAIL, roles, TOKEN_EXPIRATION);

        String token = tokenAdapter.generateToken(tokenDto, secretKey);

        assertNotNull(token);
        Claims claims = extractClaims(token);

        String[] tokenParts = token.split("\\.");
        assertEquals(3, tokenParts.length);

        assertEquals(USER_EMAIL, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(new Date()));

        assertEquals(USER_ID.toString(), claims.get("id", String.class));
        assertEquals(USER_NAME, claims.get("name", String.class));

        @SuppressWarnings("unchecked")
        Map<String, Object> resourceAccess = claims.get("resource_access", Map.class);
        assertNotNull(resourceAccess);

        @SuppressWarnings("unchecked")
        List<String> tokenRoles = (List<String>) resourceAccess.get("roles");
        assertNotNull(tokenRoles);
        assertEquals(1, tokenRoles.size());
        assertEquals(USER_ROLE_ID.toString(), tokenRoles.get(0));
    }

    @Test
    void shouldGenerateValidSignInKey() {
        String signInKey = tokenAdapter.getSignInKey(secretKey);
        byte[] decodedKey = Base64.getDecoder().decode(signInKey);

        assertNotNull(signInKey);
        assertTrue(decodedKey.length > 0);
        assertDoesNotThrow(() -> Keys.hmacShaKeyFor(decodedKey));
    }

    @Test
    void shouldGenerateTokenWithCorrectExpirationTime() {
        BuildTokenDto tokenDto = createValidTokenDto();
        long currentTime = System.currentTimeMillis();

        String token = tokenAdapter.generateToken(tokenDto, secretKey);
        Claims claims = extractClaims(token);

        long expirationTime = claims.getExpiration().getTime();
        long expectedExpiration = currentTime + TOKEN_EXPIRATION;
        assertTrue(Math.abs(expirationTime - expectedExpiration) < 5000);
    }

    @Test
    void shouldThrowExceptionWhenTokenExpired() {
        BuildTokenDto tokenDto =
                new BuildTokenDto(USER_ID, USER_NAME, USER_EMAIL, Collections.singleton(RoleEnum.USER), -3600000L);

        String token = tokenAdapter.generateToken(tokenDto, secretKey);

        assertThrows(ExpiredJwtException.class, () -> extractClaims(token));
    }

    private BuildTokenDto createValidTokenDto() {
        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        return new BuildTokenDto(USER_ID, USER_NAME, USER_EMAIL, roles, TOKEN_EXPIRATION);
    }

    private Claims extractClaims(String token) {
        byte[] keyBytes = Base64.getDecoder().decode(tokenAdapter.getSignInKey(secretKey));
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
