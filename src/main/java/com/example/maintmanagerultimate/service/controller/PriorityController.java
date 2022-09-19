package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.PrioritiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priority")
@RequiredArgsConstructor
public class PriorityController {

    private final PrioritiesRepository prioritiesRepository;

    @PostMapping("/create")
    public Long createPriority(@RequestBody Priorities priority) {
        return prioritiesRepository.save(priority).getId();
    }

    @GetMapping("/get")
    public Priorities getPriority(@RequestParam Long priorityId) {
        return prioritiesRepository
                .findById(priorityId)
                .orElseThrow();
    }

    @GetMapping("/get/all")
    public List<Priorities> getCapabilities() {
        return prioritiesRepository.findAll();
    }
}
