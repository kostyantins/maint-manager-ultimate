package com.example.maintmanagerultimate.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMaintCommentsResponseDto {

    private Long id;
    private String commentText;
    private LocalDate createdDate;
}
