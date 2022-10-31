package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import org.mapstruct.factory.Mappers;

public interface MaintCommentsMapper {

    MaintCommentsMapper INSTANCE = Mappers.getMapper(MaintCommentsMapper.class);

    GetMaintCommentsResponseDto maintCommentsEntityToMaintCommentsDto(MaintComments entity);
}
