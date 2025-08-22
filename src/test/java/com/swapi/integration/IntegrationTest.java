package com.swapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swapi.dto.SwapiResponse;
import com.swapi.security.dto.LoginRequest;
import com.swapi.security.dto.LoginResponse;
import com.swapi.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private UserDetailsService userDetailsService;

    private final String secret = "abcdefghijklmnopqrstuvwxyz123456"; // 32 chars
    private final long expiration = 3600000L;

    @BeforeEach
    void setup() {
        UserDetails mockUser = TestUtil.mockUser();
        when(userDetailsService.loadUserByUsername("luke")).thenReturn(mockUser);
        SwapiResponse swapiMock = TestUtil.mockSwapiResponse();

        when(restTemplate.getForEntity(anyString(), eq(SwapiResponse.class)))
                .thenReturn(new ResponseEntity<>(swapiMock, HttpStatus.OK));
    }

    @Test
    void loginAndGetPeople() throws Exception {

        LoginRequest loginRequest = TestUtil.mockLoginRequest();
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        String loginResponseJson = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn().getResponse().getContentAsString();

        LoginResponse loginResponse = objectMapper.readValue(loginResponseJson, LoginResponse.class);

        mockMvc.perform(get("/api/people")
                        .header("Authorization", "Bearer " + loginResponse.getToken())
                        .param("page", "1")
                        .param("limit", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_records").value(2))
                .andExpect(jsonPath("$.results[0].name").value("Luke Skywalker"));
    }
}
