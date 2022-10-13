package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintException;
import com.example.maintmanagerultimate.service.services.MaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maint")
@RequiredArgsConstructor
public class MaintController {

    private final MaintRepository maintRepository;
    private final MaintService maintService;

    @PostMapping("/create")
    public ResponseEntity<CreateMaintResponseDto> createMaint(@RequestBody Maint maint) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintService
                                .createMaintAndCommentsIfPresent(maint)
                                .getId())
                        .build());
    }

    @GetMapping("/get")
    public Maint getMaint(@RequestParam Long maintId) {
        return maintRepository
                .findById(maintId)
                .orElseThrow(() -> new NoSuchMaintException(maintId));
    }

    @GetMapping("/get/all")
    public List<Maint> getMaints() {
        return maintRepository.findAll();
    }

    @GetMapping("/get/identifier")
    public Maint getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return maintRepository.findMaintByMaintIdentifier(maintIdentifier);
    }

    // todo how to return sust status code different form the default
    @DeleteMapping("/delete/{maintId}")
    public ResponseEntity<Long> deleteMaint(@PathVariable Long maintId) {
        maintRepository.deleteById(maintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(maintId);
    }

    @PutMapping("/update/fixversion/{fixVersion}/byid/{maintId}")
    public void updateMaintFixVersion(@PathVariable String fixVersion, @PathVariable Long maintId) {
        maintRepository.updateMaintFixVersion(fixVersion, maintId);
    }

    @PutMapping("/update/fixversion/{fixVersion}/{maintId}/by")
    public void updateMaintFixVersionBy(@PathVariable String fixVersion, @PathVariable Long maintId) {
        maintRepository.updateMaintFixVersionBy(fixVersion, maintId);
    }
}