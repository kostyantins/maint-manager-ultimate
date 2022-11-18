package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.repositories.CapabilityRepository;
import com.example.maintmanagerultimate.service.dto.CreateCapabilityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import com.example.maintmanagerultimate.service.exeptions.capability.NoSuhcCapabilityException;
import com.example.maintmanagerultimate.service.mappers.CapabilityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CapabilityService {

    private final CapabilityRepository capabilityRepository;

    public CreateCapabilityResponseDpo createCapability(Capability capability) {
        final var createdCapability = capabilityRepository.save(capability);

        return CreateCapabilityResponseDpo.builder()
                .capabilityId(capabilityRepository.save(createdCapability).getId())
                .build();
    }

    public GetCapabilityResponseDto getCapability(Long capabilityId) {
        final var createdCapability = capabilityRepository
                .findById(capabilityId)
                .orElseThrow(() -> new NoSuhcCapabilityException(capabilityId));

        return CapabilityMapper.INSTANCE.capabilityEntityToCapabilityDto(createdCapability);
    }

    public List<GetCapabilityResponseDto> getCapabilities() {
        final var capability = capabilityRepository.findAll();

        return capability.stream()
                .map(CapabilityMapper.INSTANCE::capabilityEntityToCapabilityDto)
                .collect(Collectors.toList());
    }
}
