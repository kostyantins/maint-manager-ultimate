package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMainResponseDto;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintIdentifierException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintToDeleteException;
import com.example.maintmanagerultimate.service.mappers.MaintMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintService {

    private final MaintRepository maintRepository;

    public ResponseEntity<CreateMaintResponseDto> createMaintAndCommentsIfPresent(Maint maint) {
        if (maint.getComments() != null && !maint.getComments().isEmpty()) {
            maint
                    .getComments()
                    .forEach(maintComments -> maintComments.setMaint(maint));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintRepository.save(maint).getId())
                        .build());
    }

//todo investigate why it throws: Could not write JSON: failed to lazily initialize a collection of role:
// com.example.maintmanagerultimate.persistence.entities.Maint.comments, could not initialize proxy - no Session;
// nested exception is com.fasterxml.jackson.databind.JsonMappingException:
// failed to lazily initialize a collection of role:
// com.example.maintmanagerultimate.persistence.entities.Maint.comments, could not initialize proxy - no Session
// (through reference chain: java.util.ArrayList[0]->com.example.maintmanagerultimate.persistence.entities.Maint[\"comments\"])"

//    public ResponseEntity<GetMainResponseDto> getMaint(Long maintId) {
//        final var maint = maintRepository
//                .findById(maintId)
//                .orElseThrow(() -> new NoSuchMaintException(maintId));
//
//        final var mappedMaint = MaintMapper.INSTANCE.maintEntityToMaintDto(maint);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(mappedMaint);
//    }

    //todo investigate the issue with StackOverflow
    public ResponseEntity<GetMainResponseDto> getMaintFetchComment(Long maintId) {
        final var maint = Optional.ofNullable(maintRepository
                        .findByIdFetchComment(maintId))
                .orElseThrow(() -> new NoSuchMaintException(maintId));

        final var mappedMaint = MaintMapper.INSTANCE.maintEntityToMaintDto(maint);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaint);
    }

    public ResponseEntity<List<GetMainResponseDto>> getMaints() {
        final var maints = maintRepository.findAll();

        if (maints.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        final var mappedMaints = maints.stream()
                .map(MaintMapper.INSTANCE::maintEntityToMaintDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaints);
    }

    public ResponseEntity<GetMainResponseDto> getMaintByIdIdentifier(String maintIdentifier) {
        final var maint = Optional.ofNullable(maintRepository.findMaintByMaintIdentifier(maintIdentifier))
                .orElseThrow(() -> new NoSuchMaintIdentifierException(maintIdentifier));

        final var mappedMaint = MaintMapper.INSTANCE.maintEntityToMaintDto(maint);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaint);
    }

    public ResponseEntity<HttpStatus> deleteMaint(Long maintId) {
        try {
            maintRepository.deleteById(maintId);
        } catch (Exception e) {
            throw new NoSuchMaintToDeleteException(maintId);
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    public ResponseEntity<HttpStatus> updateMaintFixVersion(String fixVersion, Long maintId) {
        final var maint = Optional.ofNullable(maintRepository
                        .findByIdFetchComment(maintId))
                .orElseThrow(() -> new NoSuchMaintException(maintId));

        maint.setFixVersion(fixVersion);

        createMaintAndCommentsIfPresent(maint);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
