package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintProfile;
import com.example.maintmanagerultimate.persistence.repositories.MaintProfileRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.mappers.MaintProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaintProfileService {

    private final MaintProfileRepository maintProfileRepository;
    private final MaintRepository maintRepository;

    @Autowired
    private final MaintProfileMapper maintProfileMapper;

    @Transactional
    public CreateMaintProfileResponseDto createMaintProfile(CreateMaintProfileRequestDto maintProfile) {
        final MaintProfile maintProfileEntity = maintProfileMapper.fromCreateMaintProfileRequestDtoToMaintProfileEntity(maintProfile);

        final Maint maint = maintRepository.findById(1L).orElseThrow(() -> new NoSuchMaintException(1L));

        maint.addProfile(maintProfileEntity);

        return CreateMaintProfileResponseDto.builder()
                .maintProfileId(maintProfileEntity.getId())
                .build();
    }
}
