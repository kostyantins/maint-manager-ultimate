package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintProfileInterface;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.services.MaintProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GetMaintProfileResponseDto> getMaint(Long maintProfileId) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMaintProfile(Long maintProfileId) {
        return null;
    }
}
