package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintInterface;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.services.MaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/maints")
@RequiredArgsConstructor
public class MaintController implements MaintInterface {

    private final MaintService maintService;

    @PostMapping
    @Override
    public ResponseEntity<CreateMaintResponseDto> createMaint(@RequestBody CreateMaintRequestDto maint) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintService.createMaint(maint).getMaintId())
                        .build());
    }

    @GetMapping("{maintId}")
    @Override
    public ResponseEntity<GetMaintResponseDto> getMaint(@PathVariable Long maintId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintFetchComment(maintId));
    }

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

    @GetMapping("identifier")
    @Override
    public ResponseEntity<GetMaintResponseDto> getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintByIdIdentifier(maintIdentifier));
    }

    @DeleteMapping("{maintId}")
    @Override
    public ResponseEntity<HttpStatus> deleteMaint(@PathVariable Long maintId) {
        maintService.deleteMaint(maintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("fixversion")
    @Override
    public ResponseEntity<HttpStatus> patchMaintFixVersion(@RequestBody FixVersionRequestDto fixVersionRequestDto) {
        maintService.patchMaintFixVersion(fixVersionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping
    @Override
    public ResponseEntity<HttpStatus> updateMaint(@RequestBody UpdateMaintDto updateMaintDto) {
        maintService.updateMaint(updateMaintDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/requests")
    @Override
    public ResponseEntity<RequestsDto> getMaintRequests(@RequestParam Long requestsId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintService.getMaintRequests(requestsId));
    }
}
