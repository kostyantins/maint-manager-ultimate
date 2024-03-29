package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintIdentifierException;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintToDeleteException;
import com.example.maintmanagerultimate.service.mappers.MaintMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintService {

    private final MaintRepository maintRepository;
    private final MaintCommentsRepository maintCommentsRepository;

    @Autowired
    private final MaintMapper maintMapper;

    @Transactional
    public CreateMaintResponseDto createMaint(CreateMaintRequestDto maint) {
        final var maintEntity = maintMapper.createMaintDtoToMaintEntity(maint);

        final var savedMaint = maintRepository.save(maintEntity);

        if (maint.getComment() != null) {
            savedMaint.addComment(new MaintComments(maint.getComment().getCommentText(), savedMaint));
        }

        return CreateMaintResponseDto.builder()
                .maintId(savedMaint.getId())
                .build();
    }

    public GetMaintResponseDto getMaintFetchComment(Long maintId) {
        final var maint = Optional.ofNullable(maintRepository.findByIdFetchComment(maintId))
                .orElseThrow(() -> new NoSuchMaintException(maintId));

        return maintMapper.maintEntityToMaintDto(maint);
    }

    @Transactional
    public List<GetMaintResponseDto> getMaints() {
        final var maints = maintRepository.findAll();

        return maints.stream().map(maintMapper::maintEntityToMaintDto).collect(Collectors.toList());
    }

    @Transactional
    public GetMaintResponseDto getMaintByIdIdentifier(String maintIdentifier) {
        final var maint = Optional.ofNullable(maintRepository.findMaintByMaintIdentifier(maintIdentifier))
                .orElseThrow(() -> new NoSuchMaintIdentifierException(maintIdentifier));

        return maintMapper.maintEntityToMaintDto(maint);
    }

    public void deleteMaint(Long maintId) {
        try {
            maintRepository.deleteById(maintId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchMaintToDeleteException(maintId);
        }
    }

    public void patchMaintFixVersion(FixVersionRequestDto fixVersionRequestDto) {
        final var maint = Optional.ofNullable(maintRepository.findByIdFetchComment(fixVersionRequestDto.getMaintId()))
                .orElseThrow(() -> new NoSuchMaintException(fixVersionRequestDto.getMaintId()));

        maint.setFixVersion(fixVersionRequestDto.getFixVersion());

        final var savedMaint = maintRepository.save(maint);

        if (savedMaint.getComments() != null && !savedMaint.getComments().isEmpty()) {
            savedMaint.getComments()
                    .forEach(comment -> comment.setMaint(savedMaint));
        }

        maintCommentsRepository.saveAll(savedMaint.getComments());
    }

    @Transactional
    public void updateMaint(UpdateMaintDto updateMaintDto) {
        final var maint = Optional.of(maintRepository.getReferenceById(updateMaintDto.getId()))
                .orElseThrow(() -> new NoSuchMaintException(updateMaintDto.getId()));

        maint.setFixVersion(updateMaintDto.getFixVersion());
        maint.setClient(updateMaintDto.getClient());
        maint.setMaintIdentifier(updateMaintDto.getMaintIdentifier());
        maint.setCapabilityId(updateMaintDto.getCapabilityId());
        maint.setCreatedDate(updateMaintDto.getCreatedDate());
        maint.setDueDate(updateMaintDto.getDueDate());
        maint.setEstimate(updateMaintDto.getEstimate());
        maint.setSolvePriorityId(updateMaintDto.getSolvePriorityId());

        maintRepository.save(maint);
    }
}
