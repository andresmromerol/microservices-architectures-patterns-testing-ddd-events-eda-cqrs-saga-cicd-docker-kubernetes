package com.amr.shop.athj.auth_service_java.user.application.validate_token;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthTokenExpiredException;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthUserNotFoundException;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailQry;
import com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email.UserSearchByEmailRes;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateToken {

    private final IQueryBus queryBus;
    private final IClaimPort claimPort;

    @Autowired
    public ValidateToken(IQueryBus queryBus, IClaimPort claimPort) {
        this.queryBus = queryBus;
        this.claimPort = claimPort;
    }

    public void execute(String token) {
        log.info("Validate user token: {}", token);
        String email = getEmail(token);
        UserSearchByEmailRes user = getAuthenticatedUser(email);
        ensureUsernameIsEquivalent(token, user);
        ensureTokenDoesNotExpire(token);
    }

    private void ensureTokenDoesNotExpire(String token) {
        Date expiration = claimPort.extractExpiration(token);
        if (expiration.before(new Date())) {
            log.error("The token has expired");
            throw new UserAuthTokenExpiredException();
        }
    }

    private void ensureUsernameIsEquivalent(String token, UserSearchByEmailRes user) {
        String username = claimPort.extractUsername(token);
        if (!username.equals(user.email())) {
            log.error("The username in the token is not equal to the one in the database");
            throw new UserAuthUserNotFoundException(user.email());
        }
    }

    private String getEmail(String token) {
        String resp = claimPort.extractUsername(token);
        if (resp == null) {
            log.error("The user was not found in the token");
            throw new UserAuthException("User not found");
        }
        return resp;
    }

    private UserSearchByEmailRes getAuthenticatedUser(String email) {
        UserSearchByEmailRes res = queryBus.ask(new UserSearchByEmailQry(email));
        if (res.isEmpty()) {
            log.error("The user was not found with email: {}", email);
            throw new UserAuthException("User not found with email: " + email);
        }
        return res;
    }
}
