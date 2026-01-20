package com.swapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SwapiResponse {
    private int total_records;
    private int total_pages;
    private int current_page;
    private List<Map<String,Object>> results;

}
