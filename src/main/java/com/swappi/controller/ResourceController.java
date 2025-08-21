package com.swappi.controller;

import com.swappi.dto.SwapiFilterResponse;
import com.swappi.dto.SwapiResponse;
import com.swappi.service.SwapiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private final SwapiService service;

    public ResourceController(SwapiService service) {
        this.service = service;
    }

    @GetMapping("/{resource}")
    public SwapiResponse getResources(
            @PathVariable String resource,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return service.getResources(resource, page, limit);
    }

    @GetMapping("/{resource}/name/{name}")
    public SwapiFilterResponse getResourcesByName(
            @PathVariable String resource,
            @PathVariable String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit)
            {
        return service.getResourcesByName(resource, page, limit, name);
    }


    @GetMapping("/{resource}/id/{id}")
    public Object getResourceById(@PathVariable String resource, @PathVariable String id) {
        return service.getById(resource, id);
    }

}
