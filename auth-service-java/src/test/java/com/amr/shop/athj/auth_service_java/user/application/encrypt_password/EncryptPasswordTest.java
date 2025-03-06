package com.amr.shop.athj.auth_service_java.user.application.encrypt_password;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.athj.auth_service_java.user.domain.ports.IPasswordPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EncryptPasswordTest {

    @Mock
    private IPasswordPort passwordPort;

    private EncryptPassword encryptPassword;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        encryptPassword = new EncryptPassword(passwordPort);
    }

    @Test
    void shouldEncryptPasswordSuccessfully() {
        String password = "123456789";
        String expectedEncryptedPassword = "123456789";
        when(passwordPort.encode(password)).thenReturn(expectedEncryptedPassword);

        EncryptPasswordRes result = encryptPassword.execute(password);

        assertNotNull(result);
        assertEquals(expectedEncryptedPassword, result.encryptedPassword());
        verify(passwordPort, times(1)).encode(password);
    }

    @Test
    void shouldHandleEmptyPassword() {
        String emptyPassword = "";
        when(passwordPort.encode(emptyPassword)).thenReturn("");

        EncryptPasswordRes result = encryptPassword.execute(emptyPassword);

        assertNotNull(result);
        assertEquals("", result.encryptedPassword());
        verify(passwordPort, times(1)).encode(emptyPassword);
    }

    @Test
    void shouldReturnDifferentHashForSamePassword() {
        String password = "123456789";
        String firstHash = "hash1";
        String secondHash = "hash2";

        when(passwordPort.encode(password)).thenReturn(firstHash).thenReturn(secondHash);

        EncryptPasswordRes firstResult = encryptPassword.execute(password);
        EncryptPasswordRes secondResult = encryptPassword.execute(password);

        assertNotNull(firstResult);
        assertNotNull(secondResult);
        assertNotEquals(firstResult.encryptedPassword(), secondResult.encryptedPassword());
        verify(passwordPort, times(2)).encode(password);
    }

    @Test
    void shouldCreateResponseWithCorrectStructure() {
        String password = "123456789";
        String hashedPassword = "123456789";
        when(passwordPort.encode(password)).thenReturn(hashedPassword);

        EncryptPasswordRes result = encryptPassword.execute(password);

        assertNotNull(result);
        assertInstanceOf(EncryptPasswordRes.class, result);
        assertEquals(hashedPassword, result.encryptedPassword());
    }
}
