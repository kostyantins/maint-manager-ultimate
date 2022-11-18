package com.example.maintmanagerultimate.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixVersionRequestDto {

    private Long maintId;
    private String fixVersion;
}
