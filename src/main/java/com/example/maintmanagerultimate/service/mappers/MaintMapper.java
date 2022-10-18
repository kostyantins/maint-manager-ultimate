package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.service.dto.GetMainResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MaintMapper {

    MaintMapper INSTANCE = Mappers.getMapper(MaintMapper.class);

    //Use mapping annotation in the case of differences of the properties in the entity and dto
    //@Mapping(target = "id", source = "maintId")
    GetMainResponseDto maintEntityToMaintDto(Maint entity);
}
