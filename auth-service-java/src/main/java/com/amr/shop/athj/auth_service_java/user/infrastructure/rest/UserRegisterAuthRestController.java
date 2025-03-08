package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import com.amr.shop.athj.auth_service_java.user.application.user_register.UserAuthRegisterCmd;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.UserAuthRegisterRequest;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class UserRegisterAuthRestController extends ApiController {

  @Autowired
  public UserRegisterAuthRestController(IQueryBus queryBus, ICommandBus commandBus) {
    super(queryBus, commandBus);
  }

  @PutMapping("/register/{id}")
  public ResponseEntity<?> register(
      @Valid @RequestBody UserAuthRegisterRequest request, @PathVariable UUID id) {
    log.info("Starting user registration process for user with id: {}", id);
    dispatch(
        new UserAuthRegisterCmd(
            id,
            request.getName(),
            request.getEmail(),
            request.getPassword(),
            request.getPhone(),
            request.getRoles()));
    log.info("User registered successfully with id: {}", id);
    return ResponseEntity.ok().build();
  }
}
