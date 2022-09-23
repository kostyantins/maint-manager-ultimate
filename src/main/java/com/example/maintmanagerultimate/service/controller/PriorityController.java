package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.PrioritiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

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

    @PostConstruct
    public void createDefaultPriorities() {
        Stream.of(
                        Priorities.builder()
                                .id(1L)
                                .priorityName("High")
                                .build(),
                        Priorities.builder()
                                .id(2L)
                                .priorityName("Mid")
                                .build(),
                        Priorities.builder()
                                .id(2L)
                                .priorityName("Low")
                                .build()

                )
                .forEach(prioritiesRepository::save);
    }
}
