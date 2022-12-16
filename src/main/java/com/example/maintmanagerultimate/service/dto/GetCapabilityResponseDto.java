package com.example.maintmanagerultimate.service.dto;

import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCapabilityResponseDto {

    private Long id;
    private Capabilities capabilityName;
}
