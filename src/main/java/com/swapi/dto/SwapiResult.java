package com.swapi.dto;

import java.util.Map;

public class SwapiResult {
    private String _id;
    private String uid;
    private String description;
    private Map<String, Object> properties;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
