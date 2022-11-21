package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import com.example.maintmanagerultimate.service.dto.CreateCapabilityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetCapabilityResponseDto;
import com.example.maintmanagerultimate.service.services.CapabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/capabilities")
@RequiredArgsConstructor
public class CapabilityController {

    private final CapabilityService capabilityService;

    @PostMapping
    public ResponseEntity<CreateCapabilityResponseDpo> createCapability(@RequestBody Capability capability) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(capabilityService.createCapability(capability));
    }

    @GetMapping
    public ResponseEntity<GetCapabilityResponseDto> getCapability(@RequestParam Long capabilityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(capabilityService.getCapability(capabilityId));
    }

    //todo how wo solve bin conflicts conflicts
    @GetMapping("/all")
    public ResponseEntity<List<GetCapabilityResponseDto>> getCapabilities() {
        final var capability = capabilityService.getCapabilities();

        if (capability.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(capability);
    }

    //todo replace with liquibase or something
    @PostConstruct
    public void createDefaultCapabilities() {
        Stream.of(
                        Capability.builder()
                                .id(1L)
                                .capabilityName(CapabilityNames.ACCESS_CONTROL)
                                .build(),
                        Capability.builder()
                                .id(2L)
                                .capabilityName(CapabilityNames.APPROVALS)
                                .build(),
                        Capability.builder()
                                .id(3L)
                                .capabilityName(CapabilityNames.LIMITS)
                                .build()
                )
                .forEach(capabilityService::createCapability);
    }
}
