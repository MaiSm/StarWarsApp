package com.swappi.controller;

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
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam(required = false) String name) {
        return service.getResources(resource, page, limit, name);
    }

    @GetMapping("/{resource}/{id}")
    public Object getResourceById(@PathVariable String resource, @PathVariable String id) {
        return service.getById(resource, id);
    }

}
