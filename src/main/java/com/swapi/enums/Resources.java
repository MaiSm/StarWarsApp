package com.swapi.enums;

public enum Resources {
    PEOPLE("people"),
    FILMS("films"),
    STARSHIPS("starships"),
    VEHICLES("vehicles");

    private final String value;

    Resources(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Resources fromValue(String value) {
        for (Resources type : Resources.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not valid resource: " + value);
    }
}
