package com.swapi.dto;

import java.util.List;

public class SwapiFilterResponse {
    private int total_records;
    private int total_pages;
    private int current_page;
    private List<SwapiResult> result;

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<SwapiResult> getResult() {
        return result;
    }

    public void setResult(List<SwapiResult> result) {
        this.result = result;
    }
}
