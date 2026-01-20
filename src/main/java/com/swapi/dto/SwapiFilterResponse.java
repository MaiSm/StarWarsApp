package com.swapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SwapiFilterResponse {
    private int total_records;
    private int total_pages;
    private int current_page;
    private List<SwapiResult> result;
}
