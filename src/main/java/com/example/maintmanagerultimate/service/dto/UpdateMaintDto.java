package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UpdateMaintDto {

    private Long id;
    private String maintIdentifier;
    private Capabilities capabilityId;
    private LocalDate createdData;
    private LocalDate dueData;
    private Priorities solvePriorityId;
    private Integer estimate;
    private String fixVersion;
    private String client;
    private List<GetMaintCommentsResponseDto> comments;
}
