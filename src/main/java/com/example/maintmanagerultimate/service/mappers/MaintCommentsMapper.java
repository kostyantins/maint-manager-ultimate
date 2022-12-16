package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintCommentsMapper {

    GetMaintCommentsResponseDto maintCommentsEntityToMaintCommentsDto(MaintComments entity);
}
