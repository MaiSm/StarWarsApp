package com.swappi.dto;

import java.util.List;
import java.util.Map;

public class SwapiFilterResponse {
    public int total_records;
    public int total_pages;
    public int current_page;
    public List<SwapiResult> result;

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public void setResult(List<SwapiResult> result) {
        this.result = result;
    }

    public List<SwapiResult> getResult() {
        return result;
    }
}
