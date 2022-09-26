package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.MaintCommentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MaintCommentsService {

    private final MaintCommentsRepository maintCommentsRepository;

    // Not good implementation causing a plenty additional select calls from result set
//    public List<MaintCommentsDto> getIdentifiedMaintComments() {
//        return maintCommentsRepository.findAll()
//                .stream()
//                //todo it (m.getMaint().getMaintIdentifier()) should work when maint id is present into comments
//                .map(m -> new MaintCommentsDto("MAINT-1", m.getCommentText()))
//                .collect(toList());
//
//    }

    public List<MaintCommentsDto> getIdentifiedMaintComments() {
        return maintCommentsRepository.findMaintIdentifiedComments();
    }
}
