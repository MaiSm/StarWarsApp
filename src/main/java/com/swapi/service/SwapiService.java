package com.swapi.service;

import com.swapi.dto.SwapiFilterResponse;
import com.swapi.dto.SwapiResponse;
import com.swapi.dto.SwapiResult;
import com.swapi.enums.Resources;
import com.swapi.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SwapiService {

    private final RestTemplate restTemplate;
    private static final String BASE = "https://www.swapi.tech/api";

    public SwapiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getResources(String resource, Integer page, Integer limit) {

        Resources.fromValue(resource);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("page", page)
                .queryParam("limit", limit);


        if ("films".equalsIgnoreCase(resource)) {
            ResponseEntity<SwapiFilterResponse> res =
                    restTemplate.getForEntity(uri.toUriString(), SwapiFilterResponse.class);
            SwapiFilterResponse response = res.getBody();

            if (response == null) {
                throw new NoSuchElementException("There is no response");
            }
            return formatResult(response, page, limit);
        }

        ResponseEntity<SwapiResponse> res = restTemplate.getForEntity(uri.toUriString(), SwapiResponse.class);
        SwapiResponse response = res.getBody();

        if(response != null){
            response.setCurrent_page(page);
        }

        return response;
    }

    public SwapiFilterResponse getResourcesByName(String resource, Integer page, Integer limit, String name) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(BASE + "/" + resource)
                .queryParam("name", name);

        ResponseEntity<SwapiFilterResponse> res = restTemplate.getForEntity(uri.toUriString(), SwapiFilterResponse.class);

        SwapiFilterResponse response = res.getBody();

        if (response == null) {
            throw new NoSuchElementException("There is no response");
        }

        return formatResult(response, page, limit);
    }

    public Object getById(String resource, String id) {
        String url = BASE + "/" + resource + "/" + id;
        return restTemplate.getForObject(url, Object.class);
    }

    public SwapiFilterResponse formatResult (SwapiFilterResponse response, Integer page, Integer limit){

        int total_records = 0;
        int total_pages = 0;
        List<SwapiResult> pagedResult = new ArrayList<>();

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

}
