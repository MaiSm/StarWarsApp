package com.swapi.service;

import com.swapi.dto.SwapiFilterResponse;
import com.swapi.dto.SwapiResponse;
import com.swapi.dto.SwapiResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.swapi.util.TestUtil;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class SwapiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiService swapiService;

    @Test
    void getResources() {
        String resource = "people";
        int page = 1;
        int limit = 2;
        String expectedUri = "https://www.swapi.tech/api/people?page=1&limit=2";

        SwapiResponse mockResponse = TestUtil.mockSwapiResponse();

        when(restTemplate.getForEntity(eq(expectedUri), eq(SwapiResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        SwapiResponse response = swapiService.getResources(resource, page, limit);

        assertEquals(2, response.getTotal_records());
        assertEquals(1, response.getTotal_pages());
        assertEquals(1, response.getCurrent_page());
        assertEquals(2, response.getResults().size());
        assertEquals("Luke Skywalker", response.getResults().get(0).get("name"));
        verify(restTemplate).getForEntity(eq(expectedUri), eq(SwapiResponse.class));
    }

    @Test
    void getResourcesByName() {
        String resource = "people";
        String name = "Luke";
        int page = 1;
        int limit = 10;
        String expectedUri = "https://www.swapi.tech/api/people?name=Luke";

        SwapiFilterResponse mockFullResponse = TestUtil.mockSwapiFilterResponse();

        when(restTemplate.getForEntity(eq(expectedUri), eq(SwapiFilterResponse.class)))
                .thenReturn(new ResponseEntity<>(mockFullResponse, HttpStatus.OK));

        SwapiFilterResponse result = swapiService.getResourcesByName(resource, page, limit, name);

        assertNotNull(result);
        assertEquals(1, result.getTotal_records());
        assertEquals(1, result.getTotal_pages());
        assertEquals(1, result.getCurrent_page());
        assertEquals(1, result.getResult().size());
        verify(restTemplate).getForEntity(eq(expectedUri), eq(SwapiFilterResponse.class));
    }

    @Test
    void getResourceById() {
        String resource = "people";
        String id = "1";
        String expectedUrl = "https://www.swapi.tech/api/people/1";

        Object mockResource = new Object();

        when(restTemplate.getForObject(eq(expectedUrl), eq(Object.class)))
                .thenReturn(mockResource);

        Object result = swapiService.getById(resource, id);

        assertSame(mockResource, result);
        verify(restTemplate).getForObject(eq(expectedUrl), eq(Object.class));
    }
}
