package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMainResponseDto;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<GetMainResponseDto> getMaint(Long maintId) {
        final var maint = maintRepository
                .findById(maintId)
                .orElseThrow(() -> new NoSuchMaintException(maintId));

        final var mappedMaint = Mappers.getMapper(GetMainResponseDto.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaint);
    }
}
