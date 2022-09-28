package com.example.maintmanagerultimate.service.errors;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ResponseError {

    private Integer status;
    private String comment;
    private LocalDate date;
}
