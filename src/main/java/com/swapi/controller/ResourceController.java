package com.swapi.controller;

import com.swapi.service.SwapiService;
import com.swapi.dto.SwapiResponse;
import com.swapi.dto.SwapiFilterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private final SwapiService service;

    public ResourceController(SwapiService service) {
        this.service = service;
    }

    @GetMapping("/{resource}")
    public ResponseEntity<?> getResources(
            @PathVariable String resource,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

            Object response = service.getResources(resource, page, limit);
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{resource}/name/{name}")
    public ResponseEntity<?> getResourcesByName(
            @PathVariable String resource,
            @PathVariable String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

            SwapiFilterResponse response = service.getResourcesByName(resource, page, limit, name);
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{resource}/id/{id}")
    public ResponseEntity<?> getResourceById(
            @PathVariable String resource,
            @PathVariable String id) {

            Object response = service.getById(resource, id);
            return ResponseEntity.ok(response);
    }
}
