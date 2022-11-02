package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCapabilityResponseDto {

    private Long id;
    private CapabilityNames capabilityName;
}
