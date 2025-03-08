package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.shop.athj.auth_service_java.user.application.user_register.UserAuthRegisterCmd;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.UserAuthRegisterRequest;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

class UserRegisterAuthRestControllerTest {

  private static final UUID USER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
  private static final String USER_NAME = "andres";
  private static final String USER_EMAIL = "andres@email.com";
  private static final String USER_PASSWORD = "123456789";
  private static final String USER_PHONE = "3209118911";
  private MockMvc mockMvc;

  @Mock private IQueryBus queryBus;

  @Mock private ICommandBus commandBus;

  private ObjectMapper objectMapper;
  private UserRegisterAuthRestController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new UserRegisterAuthRestController(queryBus, commandBus);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new ResponseEntityExceptionHandler() {})
            .build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void shouldRegisterUserSuccessfully() throws Exception {
    Set<UUID> roleUuids = new HashSet<>();
    roleUuids.add(RoleEnum.USER.getId());
    UserAuthRegisterRequest request = new UserAuthRegisterRequest();
    request.setName(USER_NAME);
    request.setEmail(USER_EMAIL);
    request.setPassword(USER_PASSWORD);
    request.setPhone(USER_PHONE);
    request.setRoleUuids(roleUuids);
    String requestJson =
        String.format(
            """
                        {
                            "name": "%s",
                            "email": "%s",
                            "password": "%s",
                            "phone": "%s",
                            "role": ["%s"]
                        }
                        """,
            USER_NAME, USER_EMAIL, USER_PASSWORD, USER_PHONE, RoleEnum.USER.getId());
    mockMvc
        .perform(
            put("/api/v1/user/register/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
    verify(commandBus).dispatch(any(UserAuthRegisterCmd.class));
  }

  @Test
  void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/user/register/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenIdIsInvalid() throws Exception {
    String requestJson =
        String.format(
            """
                        {
                            "name": "%s",
                            "email": "%s",
                            "password": "%s",
                            "phone": "%s",
                            "role": ["%s"]
                        }
                        """,
            USER_NAME, USER_EMAIL, USER_PASSWORD, USER_PHONE, RoleEnum.USER.getId());
    mockMvc
        .perform(
            put("/api/v1/user/register/invalid-uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isBadRequest());
  }
}
