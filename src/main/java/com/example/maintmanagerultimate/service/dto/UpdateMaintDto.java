package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UpdateMaintDto {

    private Long id;
    private String maintIdentifier;
    private Long capabilityId;
    private LocalDate createdData;
    private LocalDate dueData;
    private Integer solvePriorityId;
    private Integer estimate;
    private String fixVersion;
    private String client;
    private List<GetMaintCommentsResponseDto> comments;
}
