package com.example.domain;

import lombok.Data;

@Data
public class TaskEventDTO {
    private Long id;
    private StatusType status;
}
