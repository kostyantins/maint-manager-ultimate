package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.PrioritiesNames;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPriorityResponseDto {

    private Long id;
    private PrioritiesNames prioritiesName;
}
