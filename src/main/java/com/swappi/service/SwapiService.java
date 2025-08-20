package com.swappi.service;

import com.swappi.dto.SwapiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SwapiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE = "https://www.swapi.tech/api";

    public SwapiResponse getResources(String resource, Integer page, Integer limit, String name) {
        SwapiResponse response = new SwapiResponse();

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("page", page)
                .queryParam("limit", limit);
        if (name != null && !name.isEmpty()) {
            uri.queryParam("name", name);
        }
        ResponseEntity<SwapiResponse> resp = restTemplate.getForEntity(uri.toUriString(), SwapiResponse.class);

        response = resp.getBody();
        response.setCurrent_page(page);

        return response;
    }

    public Object getById(String resource, String id) {
        String url = BASE + "/" + resource + "/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

}
