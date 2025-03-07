package com.amr.shop.gwy.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.gwy.gateway.exception.GatewayTokenInvalidException;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTest {

  private final String validSecretKey =
      "6F7564526E4A586C6F336437754C35766B594E5138394650425378586C414F69";

  @InjectMocks private JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(jwtUtil, "secretKey", validSecretKey);
  }

  @Test
  void extractAllClaimsShouldThrowExceptionForInvalidToken() {
    String invalidToken = "invalid.token";
    assertThrows(
        GatewayTokenInvalidException.class,
        () -> {
          jwtUtil.extractAllClaims(invalidToken);
        });
  }

  @Test
  void getSignInKeyShouldReturnKeyForValidSecretKey() {
    assertDoesNotThrow(
        () -> {
          Object key = ReflectionTestUtils.invokeMethod(jwtUtil, "getSignInKey");
          assertNotNull(key);
        });
  }

  @Test
  void getSignInKeyShouldThrowExceptionForInvalidSecretKey() {
    ReflectionTestUtils.setField(jwtUtil, "secretKey", "invalid-key");
    assertThrows(
        GatewayTokenInvalidException.class,
        () -> {
          ReflectionTestUtils.invokeMethod(jwtUtil, "getSignInKey");
        });
  }

  @Test
  void extractRolesShouldExtractValidRoles() {
    JwtUtil spyJwtUtil = spy(jwtUtil);
    Claims mockClaims = mock(Claims.class);
    Map<String, Object> resourceMap = new HashMap<>();
    resourceMap.put("roles", List.of(RoleEnum.ADMIN.getId().toString()));
    doReturn(mockClaims).when(spyJwtUtil).extractAllClaims(anyString());
    when(mockClaims.get("resource_access")).thenReturn(resourceMap);
    Set<String> roles = spyJwtUtil.extractRoles("valid.token.here");
    assertNotNull(roles);
    assertFalse(roles.isEmpty());
    assertEquals(1, roles.size());
    assertTrue(roles.contains(RoleEnum.ADMIN.getId().toString()));
  }

  @Test
  void extractRolesShouldHandleMissingResourceAccess() {
    JwtUtil spyJwtUtil = spy(jwtUtil);
    Claims mockClaims = mock(Claims.class);
    doReturn(mockClaims).when(spyJwtUtil).extractAllClaims(anyString());
    when(mockClaims.get("resource_access")).thenReturn(null);
    Set<String> roles = spyJwtUtil.extractRoles("valid.token");
    assertNotNull(roles);
    assertTrue(roles.isEmpty());
  }
}
