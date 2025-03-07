package com.amr.shop.athj.auth_service_java.token.infrastructure.token;

import com.amr.shop.athj.auth_service_java.token.domain.ITokenPort;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenAdapter implements ITokenPort {
    @Override
    public String generateToken(BuildTokenDto token, String secretKey) {
        log.info("Generating token for user {}", token.getUsername());

        Map<String, Object> claims = token.buildClaims();
        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setSubject(token.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + token.getExpiration()))
                .signWith(getSignInKeySecret(secretKey), SignatureAlgorithm.HS256)
                .compact();

        log.info("Token generated successfully for user {}", token.getUsername());
        return tokenString;
    }

    private Key getSignInKeySecret(String secretKey) {
        log.debug("Getting sign-in key secret");

        String key = getSignInKey(secretKey);
        byte[] keyBytes = Base64.getDecoder().decode(key);

        log.debug("Sign-in key secret obtained");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getSignInKey(String secretKey) {
        log.debug("Decoding secret key");

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        log.debug("Secret key decoded successfully");
        return encodedKey;
    }

    @Override
    public String generateRefreshToken(BuildTokenDto token, String secretKey) {
        log.info("Generating refresh token for user {}", token.getUsername());

        String refreshToken = Jwts.builder()
                .setSubject(token.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + token.getExpiration()))
                .signWith(getSignInKeySecret(secretKey), SignatureAlgorithm.HS256)
                .compact();

        log.info("Refresh token generated successfully for user {}", token.getUsername());
        return refreshToken;
    }
}
