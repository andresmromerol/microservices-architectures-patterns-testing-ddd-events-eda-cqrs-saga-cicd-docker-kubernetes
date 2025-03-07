package com.amr.shop.athj.auth_service_java.user.infrastructure.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class ClaimAdapterTest {

    private ClaimAdapter claimAdapter;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long JWT_EXPIRATION = 86400000;
    private static final long REFRESH_EXPIRATION = 604800000;
    private Key signingKey;

    @BeforeEach
    void setUp() {
        claimAdapter = new ClaimAdapter();
        ReflectionTestUtils.setField(claimAdapter, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(claimAdapter, "jwtExpiration", JWT_EXPIRATION);
        ReflectionTestUtils.setField(claimAdapter, "refreshExpiration", REFRESH_EXPIRATION);
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void shouldExtractUsername() {
        String expectedEmail = "andres@email.com";
        String token = generateToken(expectedEmail, new Date(System.currentTimeMillis() + JWT_EXPIRATION));

        String extractedUsername = claimAdapter.extractUsername(token);

        assertEquals(expectedEmail, extractedUsername);
    }

    @Test
    void shouldExtractExpiration() {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION);
        String token = generateToken("andres@email.com", expirationDate);

        Date extractedExpiration = claimAdapter.extractExpiration(token);

        assertNotNull(extractedExpiration);
        assertEquals(expirationDate.getTime() / 1000, extractedExpiration.getTime() / 1000);
    }

    private String generateToken(String username, Date expiration) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
