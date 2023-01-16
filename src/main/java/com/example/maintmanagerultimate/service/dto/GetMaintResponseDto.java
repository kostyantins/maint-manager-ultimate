package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMaintResponseDto {

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
