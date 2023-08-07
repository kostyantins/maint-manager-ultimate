package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetMaintProfileResponseDto {

    private Long id;
    private LocalDate pbrPlanedDate;
    private LocalDate pbrRealDate;
    private String pbrConclusion;
    private String definitionOfReady;
    private String definitionOfDone;
}
