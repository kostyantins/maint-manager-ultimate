package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.MaintProfile;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintProfileMapper {

    CreateMaintProfileRequestDto fromMaintProfileEntityToCreateMaintProfileRequestDto(MaintProfile entity);

    MaintProfile fromCreateMaintProfileRequestDtoToMaintProfileEntity(CreateMaintProfileRequestDto dto);
}
