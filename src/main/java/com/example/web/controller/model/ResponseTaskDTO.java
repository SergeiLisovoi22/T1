package com.example.web.controller.model;

import lombok.Data;

@Data
public class ResponseTaskDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
}
