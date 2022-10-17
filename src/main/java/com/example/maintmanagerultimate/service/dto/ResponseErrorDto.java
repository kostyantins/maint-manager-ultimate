package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ResponseErrorDto {

    private Integer status;
    private String comment;
    private LocalDate date;
}
