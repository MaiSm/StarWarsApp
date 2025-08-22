package com.swapi.security.controller;

import com.swapi.security.dto.LoginRequest;
import com.swapi.security.dto.LoginResponse;
import com.swapi.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    private final String secret = "abcdefghijklmnopqrstuvwxyz123456"; // 32 chars
    private final long expiration = 3600000L;

    @BeforeEach
    void setup() throws Exception {
        // Setear valores de @Value con Reflection
        java.lang.reflect.Field secretField = AuthController.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(authController, secret);

        java.lang.reflect.Field expField = AuthController.class.getDeclaredField("expiration");
        expField.setAccessible(true);
        expField.set(authController, expiration);
    }

    @Test
    void login_withValidCredentials() {
        LoginRequest req = TestUtil.mockLoginRequest();
        UserDetails mockUser = TestUtil.mockUser();

        when(userDetailsService.loadUserByUsername("luke")).thenReturn(mockUser);

        ResponseEntity<?> response = authController.login(req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof LoginResponse);

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertNotNull(loginResponse.getToken());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());
        assertFalse(loginResponse.getToken().isEmpty());

        String token = loginResponse.getToken();
        String username = io.jsonwebtoken.Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        assertEquals("luke", username);

        verify(userDetailsService).loadUserByUsername("luke");
    }

    @Test
    void login_withInvalidPassword() {
        LoginRequest req = new LoginRequest();
        req.setUsername("luke");
        req.setPassword("wrong");

        UserDetails mockUser = TestUtil.mockUser();
        when(userDetailsService.loadUserByUsername("luke")).thenReturn(mockUser);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authController.login(req));

        assertEquals("Not valid credentials", exception.getMessage());
        verify(userDetailsService).loadUserByUsername("luke");
    }

    @Test
    void login_withUnknownUser() {
        LoginRequest req = new LoginRequest();
        req.setUsername("unknown");
        req.setPassword("force");

        when(userDetailsService.loadUserByUsername("unknown")).thenReturn(null);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authController.login(req));

        assertEquals("Not valid credentials", exception.getMessage());
        verify(userDetailsService).loadUserByUsername("unknown");
    }
}
