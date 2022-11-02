package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.enums.PrioritiesNames;
import com.example.maintmanagerultimate.service.dto.CreatePriorityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetPriorityResponseDto;
import com.example.maintmanagerultimate.service.services.PrioritiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/priorities")
@RequiredArgsConstructor
public class PriorityController {

    private final PrioritiesService prioritiesService;

    @PostMapping
    public ResponseEntity<CreatePriorityResponseDpo> createPriority(@RequestBody Priorities priority) {
        return prioritiesService.createCapability(priority);
    }

    @GetMapping
    public ResponseEntity<GetPriorityResponseDto> getPriority(@RequestParam Long priorityId) {
        return prioritiesService.getPriority(priorityId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetPriorityResponseDto>> getCapabilities() {
        return prioritiesService.getPriorities();
    }

    //todo replace with liquibase or something
    @PostConstruct
    public void createDefaultPriorities() {
        Stream.of(
                        Priorities.builder()
                                .id(1L)
                                .priorityName(PrioritiesNames.HIGH)
                                .build(),
                        Priorities.builder()
                                .id(2L)
                                .priorityName(PrioritiesNames.MID)
                                .build(),
                        Priorities.builder()
                                .id(3L)
                                .priorityName(PrioritiesNames.LOW)
                                .build()

                )
                .forEach(prioritiesService::createCapability);
    }
}
