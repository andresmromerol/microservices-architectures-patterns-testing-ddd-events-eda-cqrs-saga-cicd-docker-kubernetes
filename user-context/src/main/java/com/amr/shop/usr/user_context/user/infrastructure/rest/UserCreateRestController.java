package com.amr.shop.usr.user_context.user.infrastructure.rest;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.amr.shop.cmmj.common_java_context.shared.rest.ApiController;
import com.amr.shop.usr.user_context.user.application.create_user.administrator.UserCreateAdministratorCmd;
import com.amr.shop.usr.user_context.user.application.create_user.customer.UserCreateCustomerCmd;
import com.amr.shop.usr.user_context.user.infrastructure.rest.request.UserAdministratorRequest;
import com.amr.shop.usr.user_context.user.infrastructure.rest.request.UserCustomerRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UserCreateRestController extends ApiController {

  protected UserCreateRestController(IQueryBus queryBus, ICommandBus commandBus) {
    super(queryBus, commandBus);
  }

  @PutMapping("/administrators/{id}")
  public ResponseEntity<String> createUserAdministrator(
      @PathVariable String id, @Valid @RequestBody UserAdministratorRequest administrator) {
    dispatch(
        new UserCreateAdministratorCmd(
            UUID.fromString(id),
            administrator.getName(),
            administrator.getEmail(),
            UUID.fromString(administrator.getCreatedByAdminId()),
            administrator.getPhone()));
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/customers/{id}")
  public ResponseEntity<String> createUserCustomer(
      @PathVariable String id, @Valid @RequestBody UserCustomerRequest customer) {
    dispatch(
        new UserCreateCustomerCmd(
            UUID.fromString(id),
            customer.getName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getAddress()));
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
