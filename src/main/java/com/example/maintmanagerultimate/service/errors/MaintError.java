package com.example.maintmanagerultimate.service.errors;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class MaintError {

    private Integer status;
    private String comment;
    private LocalDate date;
}
