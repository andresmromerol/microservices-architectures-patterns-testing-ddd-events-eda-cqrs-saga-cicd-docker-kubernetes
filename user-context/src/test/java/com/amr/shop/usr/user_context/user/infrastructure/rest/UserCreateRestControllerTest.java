package com.amr.shop.usr.user_context.user.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.usr.user_context._shared.infrastructure.exception.UserExceptionHdr;
import com.amr.shop.usr.user_context.user.application.create_user.administrator.UserCreateAdministratorCmd;
import com.amr.shop.usr.user_context.user.application.create_user.customer.UserCreateCustomerCmd;
import com.amr.shop.usr.user_context.user.domain.UserAlreadyExistsException;
import com.amr.shop.usr.user_context.user.infrastructure.rest.request.UserAdministratorRequest;
import com.amr.shop.usr.user_context.user.infrastructure.rest.request.UserCustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UserCreateRestControllerTest {

  private MockMvc mockMvc;

  @Mock private ICommandBus commandBus;

  @InjectMocks private UserCreateRestController userCreateRestController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc =
        MockMvcBuilders.standaloneSetup(userCreateRestController)
            .setControllerAdvice(new UserExceptionHdr())
            .build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void createUserAdministrator_ShouldReturnCreated() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID createdByAdminId = UUID.randomUUID();
    UserAdministratorRequest request =
        new UserAdministratorRequest(
            "user", "user@email.com", createdByAdminId.toString(), "3209118911");
    doNothing().when(commandBus).dispatch(any(UserCreateAdministratorCmd.class));
    mockMvc
        .perform(
            put("/api/v1/administrators/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
    verify(commandBus).dispatch(any(UserCreateAdministratorCmd.class));
  }

  @Test
  void createUserAdministrator_WithInvalidData_ShouldReturnBadRequest() throws Exception {
    UUID userId = UUID.randomUUID();
    UserAdministratorRequest request =
        new UserAdministratorRequest("", "email", "uuid", "3209118911");
    mockMvc
        .perform(
            put("/api/v1/administrators/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    verify(commandBus, never()).dispatch(any(UserCreateAdministratorCmd.class));
  }

  @Test
  void createUserAdministrator_WhenUserAlreadyExists_ShouldReturnConflict() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID createdByAdminId = UUID.randomUUID();
    UserAdministratorRequest request =
        new UserAdministratorRequest(
            "user", "user@email.com", createdByAdminId.toString(), "3209118911");
    doThrow(new UserAlreadyExistsException("user@email.com"))
        .when(commandBus)
        .dispatch(any(UserCreateAdministratorCmd.class));
    mockMvc
        .perform(
            put("/api/v1/administrators/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isConflict());
    verify(commandBus).dispatch(any(UserCreateAdministratorCmd.class));
  }

  @Test
  void createUserCustomer_WithInvalidData_ShouldReturnBadRequest() throws Exception {
    UUID userId = UUID.randomUUID();
    UserCustomerRequest request = new UserCustomerRequest("", "email", "address", "3209118911");
    mockMvc
        .perform(
            put("/api/v1/customers/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    verify(commandBus, never()).dispatch(any(UserCreateCustomerCmd.class));
  }
}
