package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.MaintCommentsDto;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintCommentsException;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintCommentsService {

    private final MaintCommentsRepository maintCommentsRepository;
    private final MaintRepository maintRepository;

    // Not good implementation causing a plenty additional select calls from result set
//    public List<MaintCommentsDto> getIdentifiedMaintComments() {
//        return maintCommentsRepository.findAll()
//                .stream()
//                .map(m -> new MaintCommentsDto(m.getMaint().getMaintIdentifier(), m.getCommentText()))
//                .collect(toList());
//
//    }

    // Much better approach
    public List<MaintCommentsDto> getIdentifiedMaintComments() {
        return maintCommentsRepository.findMaintIdentifiedComments();
    }

    public MaintComments getMaintComment(Long maintCommentId) {
        maintRepository.findAll()
                .stream()
                .filter(f -> f.getComments().stream()
                        .anyMatch(m -> m.getId().equals(maintCommentId)))
                .findFirst()
                .orElseThrow(() -> new NoSuchMaintCommentsException(maintCommentId));

        //todo how to return optional correctly here
        return maintCommentsRepository
                .findById(maintCommentId)
                .get();
    }
}
