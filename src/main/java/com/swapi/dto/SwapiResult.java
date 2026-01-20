package com.swapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SwapiResult {
    private String _id;
    private String uid;
    private String description;
    private Map<String, Object> properties;

}
