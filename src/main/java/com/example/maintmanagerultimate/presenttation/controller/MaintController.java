package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
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
        return maintService.createMaintAndCommentsIfPresent(maint);
    }

    @GetMapping("{maintId}")
    public ResponseEntity<GetMaintResponseDto> getMaint(@PathVariable Long maintId) {
        return maintService.getMaintFetchComment(maintId);
    }

    @GetMapping("all")
    public ResponseEntity<List<GetMaintResponseDto>> getMaints() {
        return maintService.getMaints();
    }

    @GetMapping("identifier")
    public ResponseEntity<GetMaintResponseDto> getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return maintService.getMaintByIdIdentifier(maintIdentifier);
    }

    @DeleteMapping("{maintId}")
    public ResponseEntity<HttpStatus> deleteMaint(@PathVariable Long maintId) {
        return maintService.deleteMaint(maintId);
    }

    @PutMapping("fixversion/{fixVersion}/id/{maintId}")
    public void updateMaintFixVersion(@PathVariable String fixVersion, @PathVariable Long maintId) {
        maintService.updateMaintFixVersion(fixVersion, maintId);
    }
}
