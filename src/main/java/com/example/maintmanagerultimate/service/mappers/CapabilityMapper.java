package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CapabilityMapper {

    CapabilityMapper INSTANCE = Mappers.getMapper(CapabilityMapper.class);

    GetCapabilityResponseDto capabilityEntityToCapabilityDto(Capability entity);
}
