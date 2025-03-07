package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.athj.auth_service_java.user.application.change_password.ChangePasswordCmd;
import com.amr.shop.athj.auth_service_java.user.domain.ports.IClaimPort;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.ChangePasswordRequest;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthTitleEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.AuthUtil;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class ChangePasswordAuthRestController extends ApiController {
    private final IClaimPort claimPort;

    @Autowired
    public ChangePasswordAuthRestController(IQueryBus queryBus, ICommandBus commandBus, IClaimPort claimPort) {
        super(queryBus, commandBus);
        this.claimPort = claimPort;
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request, HttpServletRequest httpRequest) {

        log.info("Starting password change process");

        String authHeader = httpRequest.getHeader(AuthTitleEnum.AUTHORIZATION_HEADER.getValue());
        String refreshToken = AuthUtil.extractBearerToken(authHeader);
        String email = claimPort.extractUsername(refreshToken);

        log.info("Authenticating user with email: {}", email);
        dispatch(new AuthenticationTokenCmd(email, request.getCurrentPassword()));

        log.info("Changing password for user with email: {}", email);
        dispatch(new ChangePasswordCmd(
                email, request.getCurrentPassword(), request.getNewPassword(), request.getConfirmationPassword()));

        log.info("Password change process completed for user with email: {}", email);

        return ResponseEntity.ok().build();
    }
}
