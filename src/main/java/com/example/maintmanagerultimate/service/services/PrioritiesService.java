package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.PrioritiesRepository;
import com.example.maintmanagerultimate.service.dto.CreatePriorityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import com.example.maintmanagerultimate.service.dto.GetPriorityResponseDto;
import com.example.maintmanagerultimate.service.exeptions.capability.NoSuhcCapabilityException;
import com.example.maintmanagerultimate.service.exeptions.priority.NoSuchPrioritiesException;
import com.example.maintmanagerultimate.service.mappers.CapabilityMapper;
import com.example.maintmanagerultimate.service.mappers.PriorityMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchProviderException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrioritiesService {

    private final PrioritiesRepository prioritiesRepository;

    public ResponseEntity<CreatePriorityResponseDpo> createCapability(Priorities priorities) {
        final var createdPriority = prioritiesRepository.save(priorities);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreatePriorityResponseDpo.builder()
                        .priorityId(prioritiesRepository.save(createdPriority).getId())
                        .build());
    }

    public ResponseEntity<GetPriorityResponseDto> getPriority(Long priorityId) {
        final var createdCapability = prioritiesRepository
                .findById(priorityId)
                .orElseThrow(() -> new NoSuchPrioritiesException(priorityId));

        final var mappedPriority = PriorityMapper.INSTANCE.priorityEntityToPriorityDto(createdCapability);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedPriority);
    }

    public ResponseEntity<List<GetPriorityResponseDto>> getPriorities() {
        final var capability = prioritiesRepository.findAll();

        if (capability.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        final var mappedCapabilities = capability.stream()
                .map(PriorityMapper.INSTANCE::priorityEntityToPriorityDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedCapabilities);
    }
}
