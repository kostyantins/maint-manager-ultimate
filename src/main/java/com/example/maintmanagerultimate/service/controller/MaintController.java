package com.example.maintmanagerultimate.service.controller;

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
        final var createdMaint = maintRepository.save(maint);

        maintService.createCommentIfPresent(createdMaint);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(createdMaint.getId())
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

    // todo why it doesnt return body
    @DeleteMapping("/delete/{maintId}")
    public ResponseEntity<Long> deleteMaint(@PathVariable Long maintId) {
        maintRepository.deleteById(maintId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(maintId);
    }
}
