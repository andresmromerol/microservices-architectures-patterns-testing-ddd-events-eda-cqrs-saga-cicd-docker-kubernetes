package com.amr.shop.athj.auth_service_java.user.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordSecurityAdapterTest {

    private PasswordSecurityAdapter passwordSecurityAdapter;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String PASSWORD = "123456789";
    private static final String ENCODED_PASSWORD = "encoded";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordSecurityAdapter = new PasswordSecurityAdapter(passwordEncoder);
    }

    @Test
    void shouldEncodePasswordSuccessfully() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        String result = passwordSecurityAdapter.encode(PASSWORD);

        assertNotNull(result);
        assertEquals(ENCODED_PASSWORD, result);
        verify(passwordEncoder).encode(PASSWORD);
    }

    @Test
    void shouldReturnDifferentHashForSamePassword() {
        when(passwordEncoder.encode(anyString())).thenReturn("hash1").thenReturn("hash2");

        String firstHash = passwordSecurityAdapter.encode(PASSWORD);
        String secondHash = passwordSecurityAdapter.encode(PASSWORD);

        assertNotNull(firstHash);
        assertNotNull(secondHash);
        assertNotEquals(firstHash, secondHash);
        verify(passwordEncoder, times(2)).encode(PASSWORD);
    }
}
