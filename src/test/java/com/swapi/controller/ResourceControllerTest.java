package com.swapi.controller;

import com.swapi.dto.SwapiFilterResponse;
import com.swapi.dto.SwapiResponse;
import com.swapi.service.SwapiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.swapi.util.TestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ResourceControllerTest {

    @Mock
    private SwapiService swapiService;

    @InjectMocks
    private ResourceController controller;

    @Test
    void getResources() {
        String resource = "people";
        int page = 1;
        int limit = 2;

        SwapiResponse mockResponse = TestUtil.mockSwapiResponse();
        when(swapiService.getResources(resource, page, limit)).thenReturn(mockResponse);

        ResponseEntity<?> responseEntity = controller.getResources(resource, page, limit);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        SwapiResponse response = (SwapiResponse) responseEntity.getBody();

        assertNotNull(response);
        assertEquals(2, response.getResults().size());
        assertEquals("Luke Skywalker", response.getResults().get(0).get("name"));
        verify(swapiService).getResources(resource, page, limit);
    }

    @Test
    void getResourcesByName() {
        String resource = "people";
        String name = "Luke";
        int page = 1;
        int limit = 10;

        SwapiFilterResponse mockResponse = TestUtil.mockSwapiFilterResponse();
        when(swapiService.getResourcesByName(resource, page, limit, name)).thenReturn(mockResponse);

        ResponseEntity<?> responseEntity = controller.getResourcesByName(resource, name, page, limit);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        SwapiFilterResponse response = (SwapiFilterResponse) responseEntity.getBody();

        assertNotNull(response);
        assertEquals(1, response.getTotal_records());
        verify(swapiService).getResourcesByName(resource, page, limit, name);
    }

    @Test
    void getResourceById() {
        String resource = "people";
        String id = "1";
        Object mockObject = new Object();

        when(swapiService.getById(resource, id)).thenReturn(mockObject);

        ResponseEntity<?> responseEntity = controller.getResourceById(resource, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(mockObject, responseEntity.getBody());

        verify(swapiService).getById(resource, id);
    }
}