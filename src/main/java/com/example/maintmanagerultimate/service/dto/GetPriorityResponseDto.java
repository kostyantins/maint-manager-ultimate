package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.Priorities;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPriorityResponseDto {

    private Long id;
    private Priorities prioritiesName;
}
