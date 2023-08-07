package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ResponseErrorDto {

    private Integer status;
    private String error;
    private LocalDate date;
    private String path;
}
