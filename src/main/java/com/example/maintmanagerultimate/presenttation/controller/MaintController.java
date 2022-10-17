package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMainResponseDto;
import com.example.maintmanagerultimate.service.services.MaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maints")
@RequiredArgsConstructor
public class MaintController {

    private final MaintRepository maintRepository;
    private final MaintService maintService;

    @PostMapping
    public ResponseEntity<CreateMaintResponseDto> createMaint(@RequestBody Maint maint) {
        return maintService.createMaintAndCommentsIfPresent(maint);
    }

    @GetMapping
    public  ResponseEntity<GetMainResponseDto> getMaint(@RequestParam Long maintId) {
        return maintService.getMaint(maintId);
    }

    @GetMapping("/all")
    public List<Maint> getMaints() {
        return maintRepository.findAll();
    }

    @GetMapping("/identifier")
    public Maint getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return maintRepository.findMaintByMaintIdentifier(maintIdentifier);
    }

    @DeleteMapping("/{maintId}")
    public ResponseEntity deleteMaint(@PathVariable Long maintId) {
        maintRepository.deleteById(maintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/fixversion/{fixVersion}/id/{maintId}")
    public void updateMaintFixVersion(@PathVariable String fixVersion, @PathVariable Long maintId) {
        maintRepository.updateMaintFixVersion(fixVersion, maintId);
    }

    @PutMapping("/update/fixversion/{fixVersion}/{maintId}/by")
    public void updateMaintFixVersionBy(@PathVariable String fixVersion, @PathVariable Long maintId) {
        maintRepository.updateMaintFixVersionBy(fixVersion, maintId);
    }
}
