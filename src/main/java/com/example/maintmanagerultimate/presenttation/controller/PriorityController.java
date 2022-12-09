package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.presenttation.swagger.PrioritySwagger;
import com.example.maintmanagerultimate.service.dto.CreatePriorityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetPriorityResponseDto;
import com.example.maintmanagerultimate.service.services.PrioritiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priorities")
@RequiredArgsConstructor
public class PriorityController implements PrioritySwagger {

    private final PrioritiesService prioritiesService;

    @PostMapping
    @Override
    public ResponseEntity<CreatePriorityResponseDpo> createPriority(@RequestBody Priorities priority) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prioritiesService.createPriority(priority));
    }

    @GetMapping("/{priorityId}")
    @Override
    public ResponseEntity<GetPriorityResponseDto> getPriority(@PathVariable Long priorityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prioritiesService.getPriority(priorityId));
    }

    @GetMapping
    public ResponseEntity<List<GetPriorityResponseDto>> getPriorities() {
        final var capability = prioritiesService.getPriorities();

        if (capability.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(capability);
    }
}
