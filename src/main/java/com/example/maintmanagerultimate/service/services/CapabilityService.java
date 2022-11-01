package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.repositories.CapabilityRepository;
import com.example.maintmanagerultimate.service.dto.CreateCapabilityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import com.example.maintmanagerultimate.service.exeptions.capability.NoSuhcCapabilityException;
import com.example.maintmanagerultimate.service.mappers.CapabilityMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CapabilityService {

    private final CapabilityRepository capabilityRepository;

    public ResponseEntity<CreateCapabilityResponseDpo> createCapability(Capability capability) {
        final var createdCapability = capabilityRepository.save(capability);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateCapabilityResponseDpo.builder()
                        .capabilityId(capabilityRepository.save(createdCapability).getId())
                        .build());
    }

    public ResponseEntity<GetCapabilityResponseDto> getCapability(Long capabilityId) {
        final var createdCapability = capabilityRepository
                .findById(capabilityId)
                .orElseThrow(() -> new NoSuhcCapabilityException(capabilityId));

        final var mappedCapability = CapabilityMapper.INSTANCE.capabilityEntityToCapabilityDto(createdCapability);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedCapability);
    }

    public ResponseEntity<List<GetCapabilityResponseDto>> getCapabilities() {
        final var capability = capabilityRepository.findAll();

        if (capability.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        //todo fix the issue with mapstruct
        final var mappedCapabilities = capability.stream()
                .map(CapabilityMapper.INSTANCE::capabilityEntityToCapabilityDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedCapabilities);
    }
}
