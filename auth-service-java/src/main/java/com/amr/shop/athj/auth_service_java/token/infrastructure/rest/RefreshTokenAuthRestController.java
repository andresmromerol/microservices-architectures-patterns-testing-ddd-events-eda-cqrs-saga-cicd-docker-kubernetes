package com.amr.shop.athj.auth_service_java.token.infrastructure.rest;

import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorQry;
import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorRes;
import com.amr.shop.athj.auth_service_java.user.application.validate_token.ValidateTokenCmd;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthUtil;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tokens/")
@Slf4j
public class RefreshTokenAuthRestController extends ApiController {
    private final IClaimPort claimPort;

    @Autowired
    public RefreshTokenAuthRestController(IQueryBus queryBus, ICommandBus commandBus, IClaimPort claimPort) {
        super(queryBus, commandBus);
        this.claimPort = claimPort;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request) {

        String refreshToken =
                AuthUtil.extractBearerToken(request.getHeader(AuthTitleEnum.AUTHORIZATION_HEADER.getValue()));

        log.info("Refreshing token for user: {}", refreshToken);
        String email = claimPort.extractUsername(refreshToken);
        log.info("Validating token for user: {}", email);
        dispatch(new ValidateTokenCmd(refreshToken));
        log.info("Authenticating user: {}", email);
        AuthenticatorRes authenticate = ask(new AuthenticatorQry(email));
        log.info("Returning token for user: {}", email);
        return ResponseEntity.ok(RefreshTokenResponse.builder()
                .accessToken(authenticate.accessToken())
                .refreshToken(authenticate.refreshToken())
                .build());
    }
}
