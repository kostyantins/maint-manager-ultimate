package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class GetMainResponseDto {

    private Long id;
    private String maintIdentifier;
    private Long capabilityId;
    private LocalDate createdData;
    private LocalDate dueData;
    private Integer solvePriorityId;
    private Integer estimate;
    private String fixVersion;
    private String client;
    private List<MaintComments> comments;
}
