package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
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

    private final MaintService maintService;

    @PostMapping
    public ResponseEntity<CreateMaintResponseDto> createMaint(@RequestBody Maint maint) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintService.createMaintAndCommentsIfPresent(maint).getMaintId())
                        .build());
    }

    @GetMapping("{maintId}")
    public ResponseEntity<GetMaintResponseDto> getMaint(@PathVariable Long maintId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintFetchComment(maintId));
    }

    @GetMapping
    public ResponseEntity<List<GetMaintResponseDto>> getMaints() {
        final var maints = maintService.getMaints();

        if (maints.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maints);
    }

    @GetMapping("identifier")
    public ResponseEntity<GetMaintResponseDto> getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintByIdIdentifier(maintIdentifier));
    }

    @DeleteMapping("{maintId}")
    public ResponseEntity<HttpStatus> deleteMaint(@PathVariable Long maintId) {
        maintService.deleteMaint(maintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("fixversion")
    public ResponseEntity<HttpStatus> updateMaintFixVersion(@RequestBody FixVersionRequestDto fixVersionRequestDto) {
        maintService.patchMaintFixVersion(fixVersionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateMaint(@RequestBody UpdateMaintDto updateMaintDto) {
        maintService.updateMaint(updateMaintDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
