package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintInterface;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.services.MaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/maints")
@RequiredArgsConstructor
public class MaintController implements MaintInterface {

    private final MaintService maintService;

    @RolesAllowed("admin")
    @PostMapping
    @Override
    public ResponseEntity<CreateMaintResponseDto> createMaint(@RequestBody CreateMaintRequestDto maint) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintService.createMaint(maint).getMaintId())
                        .build());
    }

    @RolesAllowed({"user", "admin"})
    @GetMapping("{maintId}")
    @Override
    public ResponseEntity<GetMaintResponseDto> getMaint(@PathVariable Long maintId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintFetchComment(maintId));
    }

    @RolesAllowed({"user", "admin"})
    @GetMapping
    @Override
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

    @RolesAllowed({"user", "admin"})
    @GetMapping("identifier")
    @Override
    public ResponseEntity<GetMaintResponseDto> getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintByIdIdentifier(maintIdentifier));
    }

    @RolesAllowed("admin")
    @DeleteMapping("{maintId}")
    @Override
    public ResponseEntity<HttpStatus> deleteMaint(@PathVariable Long maintId) {
        maintService.deleteMaint(maintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RolesAllowed("admin")
    @PatchMapping("fixversion")
    @Override
    public ResponseEntity<HttpStatus> patchMaintFixVersion(@RequestBody FixVersionRequestDto fixVersionRequestDto) {
        maintService.patchMaintFixVersion(fixVersionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @RolesAllowed("admin")
    @PutMapping
    @Override
    public ResponseEntity<HttpStatus> updateMaint(@RequestBody UpdateMaintDto updateMaintDto) {
        maintService.updateMaint(updateMaintDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
