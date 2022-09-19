package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.exeptions.NoMaintCommentsException;
import com.example.maintmanagerultimate.service.exeptions.NoMaintException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maint")
@RequiredArgsConstructor
public class MaintController {

    private final MaintRepository maintRepository;

    @PostMapping("/create")
    public Long createMaint(@RequestBody Maint maint) {
        final var createdMaint = maintRepository.save(maint);

        //todo why it doesnt create maint id into DB
        if (maint.getComments() != null && !maint.getComments().isEmpty()) {
            maint.addComment(new MaintComments(maint.getComments().get(0).getCommentText(), createdMaint));
        }

        return createdMaint.getId();
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

    @DeleteMapping("/delete")
    public void deleteMaint(@RequestParam Long maintId) {
        maintRepository.deleteMaintById(maintId);
    }
}
