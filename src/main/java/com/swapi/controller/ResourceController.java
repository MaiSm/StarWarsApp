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
        try {
            SwapiResponse response = service.getResources(resource, page, limit);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/{resource}/name/{name}")
    public ResponseEntity<?> getResourcesByName(
            @PathVariable String resource,
            @PathVariable String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            SwapiFilterResponse response = service.getResourcesByName(resource, page, limit, name);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/{resource}/id/{id}")
    public ResponseEntity<?> getResourceById(
            @PathVariable String resource,
            @PathVariable String id) {
        try {
            Object response = service.getById(resource, id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
