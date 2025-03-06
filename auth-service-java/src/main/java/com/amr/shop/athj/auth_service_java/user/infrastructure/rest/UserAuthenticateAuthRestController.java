package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.UserAuthenticateRequest;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class UserAuthenticateAuthRestController extends ApiController {

    @Autowired
    public UserAuthenticateAuthRestController(IQueryBus queryBus, ICommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserAuthenticateRequest r) {
        log.info("Starting authentication for email: {}", r.getEmail());
        try {
            dispatch(new AuthenticationTokenCmd(r.getEmail(), r.getPassword()));
            log.info("Authentication successful for email: {}", r.getEmail());
            return ResponseEntity.ok().build();
        } catch (UserAuthAuthenticationFailedException e) {
            log.error("Authentication failed for email: {}", r.getEmail(), e);
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
