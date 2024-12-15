package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusType {
    CREATE("createTask"),
    UPDATE("updateTask"),
    DELETE("deleteTask");

    private final String value;

    public static StatusType fromValue(String value) {
        for (StatusType type : StatusType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value:" + value);
    }
}
