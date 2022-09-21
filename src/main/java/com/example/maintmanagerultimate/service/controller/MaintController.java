package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.exeptions.NoMaintException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/maint")
@RequiredArgsConstructor
public class MaintController {

    private final MaintRepository maintRepository;

    @PostMapping("/create")
    public ResponseEntity<Long> createMaint(@RequestBody Maint maint) {
        final var createdMaint = maintRepository.save(maint);

        if (maint.getComments() != null && !maint.getComments().isEmpty()) {
            maint.addComment(new MaintComments(maint.getComments().get(0).getCommentText(), createdMaint));
            log.debug("The comment: '{}' was added to the maint: {}",
                    maint.getComments().stream().map(Object::toString).collect(Collectors.joining()), maint.getId());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdMaint.getId());
    }

    @GetMapping("/get")
    public Maint getMaint(@RequestParam Long maintId) {
        return maintRepository
                .findById(maintId)
                .orElseThrow(() -> new NoMaintException(maintId));
    }

    @GetMapping("/get/all")
    public List<Maint> getMaints() {
        return maintRepository.findAll();
    }

    @GetMapping("/get/identifier")
    public Maint getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return maintRepository.findMaintByMaintIdentifier(maintIdentifier);
    }

    // todo why it doesnt work and how to create a right response code and do not return body
    @DeleteMapping("/delete/{maintId}")
    public ResponseEntity<?> deleteMaint(@PathVariable Long maintId) {
        maintRepository.deleteMaintById(maintId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(maintId);
    }
}
