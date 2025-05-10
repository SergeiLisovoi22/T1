package com.example.web.controller.model;

import lombok.Data;

@Data
public class RequestTaskDTO {
    private Long userId;
    private String title;
    private String description;
}
