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
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("page", page == null ? 1 : page)
                .queryParam("limit", limit == null ? 10 : limit);
        if (name != null && !name.isEmpty()) {
            uri.queryParam("name", name);
        }
        ResponseEntity<SwapiResponse> resp = restTemplate.getForEntity(uri.toUriString(), SwapiResponse.class);
        return resp.getBody();
    }

    public Object getById(String resource, String id) {
        String url = BASE + "/" + resource + "/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

}
