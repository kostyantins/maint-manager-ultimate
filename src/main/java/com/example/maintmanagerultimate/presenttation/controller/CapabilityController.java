package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import com.example.maintmanagerultimate.presenttation.swagger.CapabilitySwagger;
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
public class CapabilityController implements CapabilitySwagger {

    private final CapabilityService capabilityService;

    @PostMapping
    @Override
    public ResponseEntity<CreateCapabilityResponseDpo> createCapability(@RequestBody Capability capability) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(capabilityService.createCapability(capability));
    }

    @GetMapping("/{capabilityId}")
    @Override
    public ResponseEntity<GetCapabilityResponseDto> getCapability(@PathVariable Long capabilityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(capabilityService.getCapability(capabilityId));
    }

    @GetMapping
    @Override
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
}
