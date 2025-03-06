package com.amr.shop.athj.auth_service_java.user.infrastructure.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amr.shop.athj.auth_service_java.user.application.authentication_token.AuthenticationTokenCmd;
import com.amr.shop.athj.auth_service_java.user.domain.UserAuthAuthenticationFailedException;
import com.amr.shop.athj.auth_service_java.user.infrastructure.rest.request.UserAuthenticateRequest;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UserAuthenticateAuthRestControllerTest {

    private MockMvc mockMvc;
    private ICommandBus commandBus;
    private IQueryBus queryBus;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        commandBus = mock(ICommandBus.class);
        queryBus = mock(IQueryBus.class);
        objectMapper = new ObjectMapper();

        UserAuthenticateAuthRestController controller = new UserAuthenticateAuthRestController(queryBus, commandBus);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldAuthenticateSuccessfully() throws Exception {
        String email = "andres@email.com";
        String password = "123456789";
        UserAuthenticateRequest request = new UserAuthenticateRequest(email, password);

        doNothing().when(commandBus).dispatch(any(AuthenticationTokenCmd.class));

        mockMvc.perform(post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(commandBus).dispatch(any(AuthenticationTokenCmd.class));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        String email = "andres@email.com";
        String password = "123456789";
        UserAuthenticateRequest request = new UserAuthenticateRequest(email, password);

        doThrow(new UserAuthAuthenticationFailedException("Authentication failed"))
                .when(commandBus)
                .dispatch(any(AuthenticationTokenCmd.class));

        mockMvc.perform(post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
