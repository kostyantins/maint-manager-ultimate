package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintProfileInterface;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileRequestDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintProfileResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintProfileResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MaintProfileController implements MaintProfileInterface {

    @Override
    public ResponseEntity<CreateMaintProfileResponseDto> createMaintProfile(CreateMaintProfileRequestDto maintProfile) {
        return null;
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
