package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import com.example.maintmanagerultimate.persistence.repositories.CapabilityRepository;
import com.example.maintmanagerultimate.service.exeptions.NoCapabilityException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityController {

    private final CapabilityRepository capabilityRepository;

    @PostMapping("/create")
    public Long createCapability(@RequestBody Capability capability) {
        return capabilityRepository.save(capability).getId();
    }

    @GetMapping("/get")
    public Capability getCapability(@RequestBody Long capabilityId) {
        return capabilityRepository
                .findById(capabilityId)
                .orElseThrow(() -> new NoCapabilityException(capabilityId));
    }

    @GetMapping("/get/all")
    public List<Capability> getCapabilities() {
        return capabilityRepository.findAll();
    }

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
                .forEach(capabilityRepository::save);
    }
}
