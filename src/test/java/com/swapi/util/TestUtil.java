package com.swapi.util;

import com.swapi.dto.SwapiResponse;
import com.swapi.dto.SwapiFilterResponse;
import com.swapi.dto.SwapiResult;
import com.swapi.security.dto.LoginRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class TestUtil {

    public static SwapiResponse mockSwapiResponse() {
        SwapiResponse response = new SwapiResponse();
        response.total_records = 2;
        response.total_pages = 1;
        response.current_page = 1;

        Map<String, Object> luke = new HashMap<>();
        luke.put("uid", "1");
        luke.put("name", "Luke Skywalker");
        luke.put("url", "https://www.swapi.tech/api/people/1");

        Map<String, Object> c3po = new HashMap<>();
        c3po.put("uid", "2");
        c3po.put("name", "C-3PO");
        c3po.put("url", "https://www.swapi.tech/api/people/2");

        response.results = Arrays.asList(luke, c3po);

        return response;
    }

    public static SwapiFilterResponse mockSwapiFilterResponse() {
        SwapiFilterResponse response = new SwapiFilterResponse();
        response.total_records = 1;
        response.total_pages = 1;
        response.current_page = 1;

        SwapiResult result = new SwapiResult();
        result._id = "5f63a36eee9fd7000499be42";
        result.uid = "1";
        result.description = "A person within the Star Wars universe";

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Luke Skywalker");
        properties.put("gender", "male");

        result.properties = properties;
        response.result = Collections.singletonList(result);

        return response;
    }

    //Auth Mocks

    public static UserDetails mockUser() {
        return User.builder()
                .username("luke")
                .password("{noop}force")
                .roles("USER")
                .build();
    }

    public static LoginRequest mockLoginRequest() {
        LoginRequest req = new LoginRequest();
        req.setUsername("luke");
        req.setPassword("force");
        return req;
    }

}
