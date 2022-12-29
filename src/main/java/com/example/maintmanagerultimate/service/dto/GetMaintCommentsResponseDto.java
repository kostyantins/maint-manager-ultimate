package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetMaintCommentsResponseDto {

    private Long id;
    private String commentText;
    private LocalDate createdDate;
}
