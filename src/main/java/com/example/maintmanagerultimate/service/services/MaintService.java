package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintIdentifierException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintToDeleteException;
import com.example.maintmanagerultimate.service.mappers.MaintMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintService {

    private final MaintRepository maintRepository;

    //todo remove method, but I didnt get why((, need Artur clarification
    public CreateMaintResponseDto createMaintAndCommentsIfPresent(Maint maint) {
        if (maint.getComments() != null && !maint.getComments().isEmpty()) {
            maint.addComments(maint.getComments());
        }

        return CreateMaintResponseDto.builder()
                .maintId(maintRepository.save(maint).getId())
                .build();
    }

    public GetMaintResponseDto getMaintFetchComment(Long maintId) {
        final var maint = Optional.ofNullable(maintRepository
                        .findByIdFetchComment(maintId))
                .orElseThrow(() -> new NoSuchMaintException(maintId));

        return MaintMapper.INSTANCE.maintEntityToMaintDto(maint);
    }


    @Transactional
    public List<GetMaintResponseDto> getMaints() {
        final var maints = maintRepository.findAll();

        return maints.stream()
                .map(MaintMapper.INSTANCE::maintEntityToMaintDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public GetMaintResponseDto getMaintByIdIdentifier(String maintIdentifier) {
        final var maint = Optional.ofNullable(maintRepository.findMaintByMaintIdentifier(maintIdentifier))
                .orElseThrow(() -> new NoSuchMaintIdentifierException(maintIdentifier));

        return MaintMapper.INSTANCE.maintEntityToMaintDto(maint);
    }

    public void deleteMaint(Long maintId) {
        try {
            maintRepository.deleteById(maintId);
        } catch (Exception e) {
            throw new NoSuchMaintToDeleteException(maintId);
        }
    }

    public void patchMaintFixVersion(FixVersionRequestDto fixVersionRequestDto) {
        final var maint = Optional.ofNullable(maintRepository
                        .findByIdFetchComment(fixVersionRequestDto.getMaintId()))
                .orElseThrow(() -> new NoSuchMaintException(fixVersionRequestDto.getMaintId()));

        maint.setFixVersion(fixVersionRequestDto.getFixVersion());

        createMaintAndCommentsIfPresent(maint);
    }

    @Transactional
    public void updateMaint(UpdateMaintDto updateMaintDto) {
        final var maint = Optional.of(maintRepository
                        .getReferenceById(updateMaintDto.getId()))
                .orElseThrow(() -> new NoSuchMaintException(updateMaintDto.getId()));

        maint.setFixVersion(updateMaintDto.getFixVersion());
        maint.setClient(updateMaintDto.getClient());
        maint.setMaintIdentifier(updateMaintDto.getMaintIdentifier());
        maint.setCapabilityId(updateMaintDto.getCapabilityId());
        maint.setCreatedData(updateMaintDto.getCreatedData());
        maint.setDueData(updateMaintDto.getDueData());
        maint.setEstimate(updateMaintDto.getEstimate());
        maint.setSolvePriorityId(updateMaintDto.getSolvePriorityId());

        maintRepository.save(maint);
    }
}
