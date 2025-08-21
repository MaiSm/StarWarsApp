package com.swappi.service;

import com.swappi.dto.SwapiFilterResponse;
import com.swappi.dto.SwapiResponse;
import com.swappi.dto.SwapiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SwapiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE = "https://www.swapi.tech/api";

    public SwapiResponse getResources(String resource, Integer page, Integer limit) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("page", page)
                .queryParam("limit", limit);

        ResponseEntity<SwapiResponse> response = restTemplate.getForEntity(uri.toUriString(), SwapiResponse.class);

        return response.getBody();
    }

    public SwapiFilterResponse getResourcesByName(String resource, Integer page, Integer limit, String name) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("name", name);

        ResponseEntity<SwapiFilterResponse> res = restTemplate.getForEntity(uri.toUriString(), SwapiFilterResponse.class);

        int total_records = 0;
        int total_pages = 0;
        List<SwapiResult> pagedResult = new ArrayList<>();

        SwapiFilterResponse response = res.getBody();

        if (response.getResult() != null){

            List<SwapiResult> result = response.getResult();
            total_records =  result.size();
            total_pages = (int) Math.ceil((double)total_records / limit);

            int fromIndex = Math.min((page - 1) * limit, total_records);
            int toIndex = Math.min(fromIndex + limit, total_records);

            pagedResult = result.subList(fromIndex, toIndex);

        }
        response.setCurrent_page(page);
        response.setTotal_pages(total_pages);
        response.setTotal_records(total_records);
        response.setResult(pagedResult);

        return response;
    }

    public Object getById(String resource, String id) {
        String url = BASE + "/" + resource + "/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

}
