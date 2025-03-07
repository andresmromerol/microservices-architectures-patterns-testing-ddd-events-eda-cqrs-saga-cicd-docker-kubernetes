package com.amr.shop.athj.auth_service_java.user.application.authentication;

import com.amr.shop.athj.auth_service_java.token.application.token_generate.TokenGenerateQry;
import com.amr.shop.athj.auth_service_java.token.application.token_generate.TokenGenerateRes;
import com.amr.shop.athj.auth_service_java.token.application.token_refresh.TokenRefreshQry;
import com.amr.shop.athj.auth_service_java.token.application.token_refresh.TokenRefreshRes;
import com.amr.shop.athj.auth_service_java.token.application.token_revoke.TokenRevokeCmd;
import com.amr.shop.athj.auth_service_java.token.application.token_save.TokenSaveCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IAuthenticationPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Authentication {
    private final IQueryBus queryBus;
    private final ICommandBus commandBus;
    private final IAuthenticationPort authenticationPort;

    @Autowired
    public Authentication(IQueryBus queryBus, ICommandBus commandBus, IAuthenticationPort authenticationPort) {
        this.queryBus = queryBus;
        this.commandBus = commandBus;
        this.authenticationPort = authenticationPort;
    }

    public AuthenticatorRes execute(String email) {
        log.info("Authenticating user with email: {}", email);
        UserSearchByEmailRes user = getAuthenticatedUser(email);
        BuildTokenDto token = getToken(user);
        BuildTokenDto refreshToken = getRefreshToken(user);

        String secretKey = authenticationPort.getSecretKey();
        TokenGenerateRes tokenGenerate = queryBus.ask(new TokenGenerateQry(token, secretKey));
        TokenRefreshRes refreshTokenGenerate = queryBus.ask(new TokenRefreshQry(refreshToken, secretKey));

        log.info("Generating token for user with id: {}", user.id());
        UUID userId = user.id();
        commandBus.dispatch(new TokenRevokeCmd(userId));
        commandBus.dispatch(new TokenSaveCmd(userId, tokenGenerate.tokenGenerated()));

        log.info("Authentication finished successfully for user with id: {}", user.id());
        return new AuthenticatorRes(tokenGenerate.tokenGenerated(), refreshTokenGenerate.refreshTokenGenerated());
    }

    private BuildTokenDto getRefreshToken(UserSearchByEmailRes user) {
        long refreshExpiration = authenticationPort.refreshExpiration();
        return new BuildTokenDto(user.id(), user.name(), user.email(), user.roles(), refreshExpiration);
    }

    private BuildTokenDto getToken(UserSearchByEmailRes user) {
        long jwtExpiration = authenticationPort.getJwtExpiration();
        return new BuildTokenDto(user.id(), user.name(), user.email(), user.roles(), jwtExpiration);
    }

    private UserSearchByEmailRes getAuthenticatedUser(String email) {
        UserSearchByEmailRes res = queryBus.ask(new UserSearchByEmailQry(email));
        if (res.isEmpty()) {
            log.info("User with email: {} not found", email);
            throw new UserAuthUserNotFoundException(email);
        }
        log.info("User with email: {} found", email);
        return res;
    }
}
