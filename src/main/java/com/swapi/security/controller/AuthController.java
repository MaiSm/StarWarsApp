package com.swapi.security.controller;

import com.swapi.security.dto.LoginRequest;
import com.swapi.security.dto.LoginResponse;
import com.swapi.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        UserDetails user = uds.loadUserByUsername(req.getUsername());
        if (user == null || !user.getPassword().equals("{noop}" + req.getPassword())) {
            throw new BadCredentialsException("Not valid credentials");
        }
        String token = JwtUtil.generateToken(user.getUsername(), secret, expiration);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
