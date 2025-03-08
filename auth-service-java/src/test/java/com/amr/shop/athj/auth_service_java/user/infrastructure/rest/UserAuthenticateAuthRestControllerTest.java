package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorQry;
import com.amr.shop.athj.auth_service_java.user.application.authentication.AuthenticatorRes;
import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

class UserAuthenticateAuthRestControllerTest {

  private static final String USER_EMAIL = "andres@email.com";
  private static final String USER_PASSWORD = "123456789";
  private static final String ACCESS_TOKEN = "test.access.token";
  private static final String REFRESH_TOKEN = "test.refresh.token";
  private static final String BASE_URL = "/api/v1/user/authenticate";
  private MockMvc mockMvc;

  @Mock private ICommandBus commandBus;

  @Mock private IQueryBus queryBus;

  private ObjectMapper objectMapper;
  private UserAuthenticateAuthRestController controller;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new UserAuthenticateAuthRestController(queryBus, commandBus);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new ResponseEntityExceptionHandler() {})
            .build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void shouldAuthenticateSuccessfully() throws Exception {
    String requestJson = createValidRequestJson();
    AuthenticatorRes authenticatorRes = new AuthenticatorRes(ACCESS_TOKEN, REFRESH_TOKEN);
    when(queryBus.ask(any(AuthenticatorQry.class))).thenReturn(authenticatorRes);
    doNothing().when(commandBus).dispatch(any(AuthenticationTokenCmd.class));
    mockMvc
        .perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token").value(ACCESS_TOKEN))
        .andExpect(jsonPath("$.refresh_token").value(REFRESH_TOKEN));
    verify(queryBus).ask(any(AuthenticatorQry.class));
    verify(commandBus).dispatch(any(AuthenticationTokenCmd.class));
  }

  @Test
  void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
    mockMvc
        .perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content("invalid json"))
        .andExpect(status().isBadRequest());
  }

  private String createValidRequestJson() {
    return String.format(
        """
                        {
                            "email": "%s",
                            "password": "%s"
                        }
                        """,
        USER_EMAIL, USER_PASSWORD);
  }
}
