package com.swappi.dto;

import java.util.List;
import java.util.Map;

public class SwapiResponse {
    public int total_records;
    public int total_pages;
    public int current_page;
    public List<Map<String,Object>> results;
}
