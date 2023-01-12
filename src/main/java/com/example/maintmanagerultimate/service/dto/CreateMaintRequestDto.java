package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CreateMaintRequestDto {

    private Long id;
    private String maintIdentifier;
    private Capabilities capabilityId;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private Priorities solvePriorityId;
    private Integer estimate;
    private String fixVersion;
    private String client;
    private List<GetMaintCommentsResponseDto> comments;
}
