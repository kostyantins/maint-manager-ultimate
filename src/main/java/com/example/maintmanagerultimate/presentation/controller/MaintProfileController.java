package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintProfileInterface;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.services.MaintProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class MaintProfileController implements MaintProfileInterface {

    private final MaintProfileService maintProfileService;

    @Override
    @PostMapping
    public ResponseEntity<CreateMaintProfileResponseDto> createMaintProfile(@RequestBody CreateMaintProfileRequestDto maintProfile) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintProfileResponseDto.builder()
                        .maintProfileId(maintProfileService.createMaintProfile(maintProfile).getMaintProfileId())
                        .build());
    }

    @Override
    @GetMapping("{maintProfileId}")
    public ResponseEntity<GetMaintProfileResponseDto> getMaint(@PathVariable Long maintProfileId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintProfileService.getMaintProfile(maintProfileId));
    }

    @Override
    @DeleteMapping("{maintProfileId}")
    public ResponseEntity<HttpStatus> deleteMaintProfile(@PathVariable Long maintProfileId) {
        maintProfileService.deleteMaintProfile(maintProfileId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
