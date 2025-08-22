package com.swapi.security.controller;

import com.swapi.security.dto.LoginRequest;
import com.swapi.security.dto.LoginResponse;
import com.swapi.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsService uds;

    public AuthController(UserDetailsService uds) {
        this.uds = uds;
    }

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private long expiration;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            UserDetails user = uds.loadUserByUsername(req.getUsername());

            if (user == null || !user.getPassword().equals("{noop}" + req.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }

            String token = JwtUtil.generateToken(user.getUsername(), secret, expiration);
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Unexpected error occurred: " + e.getMessage()));
        }
    }
}
