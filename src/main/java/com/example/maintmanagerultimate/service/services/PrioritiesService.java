package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.PrioritiesRepository;
import com.example.maintmanagerultimate.service.dto.CreatePriorityResponseDpo;
import com.example.maintmanagerultimate.service.dto.GetPriorityResponseDto;
import com.example.maintmanagerultimate.service.exeptions.priority.NoSuchPrioritiesException;
import com.example.maintmanagerultimate.service.mappers.PriorityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrioritiesService {

    private final PrioritiesRepository prioritiesRepository;

    public CreatePriorityResponseDpo createPriority(Priorities priorities) {
        final var createdPriority = prioritiesRepository.save(priorities);

        return CreatePriorityResponseDpo.builder()
                .priorityId(prioritiesRepository.save(createdPriority).getId())
                .build();
    }

    public GetPriorityResponseDto getPriority(Long priorityId) {
        final var createdCapability = prioritiesRepository
                .findById(priorityId)
                .orElseThrow(() -> new NoSuchPrioritiesException(priorityId));

        return PriorityMapper.INSTANCE.priorityEntityToPriorityDto(createdCapability);
    }

    public List<GetPriorityResponseDto> getPriorities() {
        final var priorities = prioritiesRepository.findAll();

        return priorities.stream()
                .map(PriorityMapper.INSTANCE::priorityEntityToPriorityDto)
                .collect(Collectors.toList());
    }
}
