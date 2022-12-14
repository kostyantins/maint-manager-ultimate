package com.example.maintmanagerultimate.service.mappers;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.service.dto.CreateMaintRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintMapper {

    //Use mapping annotation in the case of differences of the properties in the entity and dto
    //@Mapping(target = "id", source = "maintId")
    GetMaintResponseDto maintEntityToMaintDto(Maint entity);

    CreateMaintRequestDto maintEntityToCreateMaintDto(Maint entity);

    Maint updateMaintDtoToMaintEntity(UpdateMaintDto entity);

    Maint createMaintDtoToMaintEntity(CreateMaintRequestDto entity);
}
