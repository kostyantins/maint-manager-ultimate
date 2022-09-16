package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maint")
@RequiredArgsConstructor
public class MaintController {

    private final MaintRepository maintRepository;
    private final MaintCommentsRepository maintCommentsRepository;

    @PostMapping("/create")
    public Long createMaint(@RequestBody Maint maint) {
        final var createdMaint = maintRepository.save(maint);

        //todo why it doesnt create maint id into DB
        if (maint.getComments() != null && !maint.getComments().isEmpty()){
            maint.addComment(new MaintComments(maint.getComments().get(0).getCommentText(), createdMaint));
        }

        return createdMaint.getId();
    }

    @GetMapping("/get")
    public Maint getMaint(@RequestParam Long maintId) {
        return maintRepository.findById(maintId).orElseThrow();
    }

    @GetMapping("/get/all")
    public List<Maint> getAllMaints() {
        return maintRepository.findAll();
    }

    @GetMapping("/get/identifier")
    public Maint getMaintByIdIdentifier(@RequestParam String maintIdentifier) {
        return maintRepository.findMaintByMaintIdentifier(maintIdentifier);
    }
}
