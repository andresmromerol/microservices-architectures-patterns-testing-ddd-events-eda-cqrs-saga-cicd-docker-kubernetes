package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorQry;
import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorRes;
import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.UserAuthenticateRequest;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.response.UserAuthResponse;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class UserAuthenticateAuthRestController extends ApiController {

    @Autowired
    public UserAuthenticateAuthRestController(IQueryBus queryBus, ICommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthResponse> authenticate(@RequestBody UserAuthenticateRequest r) {
        log.info("Starting authentication for email: {}", r.getEmail());
        dispatch(new AuthenticationTokenCmd(r.getEmail(), r.getPassword()));
        log.info("Authentication successful for email: {}", r.getEmail());
        log.info("Getting tokens for email: {}", r.getEmail());
        AuthenticatorRes ask = ask(new AuthenticatorQry(r.getEmail()));
        log.info("Tokens obtained for email: {}", r.getEmail());
        UserAuthResponse res = UserAuthResponse.builder()
                .accessToken(ask.accessToken())
                .refreshToken(ask.refreshToken())
                .build();
        log.info("Returning tokens for email: {}", r.getEmail());
        return ResponseEntity.ok(res);
    }
}
