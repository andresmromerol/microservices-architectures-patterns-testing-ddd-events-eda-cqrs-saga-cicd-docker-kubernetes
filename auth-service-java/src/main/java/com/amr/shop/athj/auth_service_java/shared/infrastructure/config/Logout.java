package com.amr.shop.athj.auth_service_java.shared.infrastructure.config;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.ITokenJpaDao;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpa;
import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.token.TokenJpaRepository;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Logout implements LogoutHandler {

    private final TokenJpaRepository tokenRepository;
    private final ITokenJpaDao tokenJpaDao;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(AuthTitleEnum.AUTHORIZATION_HEADER.getValue());
        String jwt = AuthUtil.extractBearerToken(authHeader);
        TokenJpa storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenJpaDao.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
