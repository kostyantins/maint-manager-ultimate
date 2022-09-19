package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.repositories.CapabilityRepository;
import com.example.maintmanagerultimate.service.exeptions.NoCapabilityException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityController {

    private final CapabilityRepository capabilityRepository;

    @PostMapping("/create")
    public Long createCapability(@RequestBody Capability capability){
        return capabilityRepository.save(capability).getId();
    }

    @GetMapping("/get")
    public Capability getCapability(@RequestBody Long capabilityId){
        return capabilityRepository
                .findById(capabilityId)
                .orElseThrow(() -> new NoCapabilityException(capabilityId));
    }

    @GetMapping("/get/all")
    public List<Capability> getCapabilities(){
        return capabilityRepository.findAll();
    }
}
