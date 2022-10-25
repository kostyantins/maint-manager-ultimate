package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.exeptions.maint_comments.NoSuchMaintCommentsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintCommentsService {

    private final MaintCommentsRepository maintCommentsRepository;

    // Not good implementation causing a plenty additional select calls from result set
//    public List<MaintCommentsDto> getIdentifiedMaintComments() {
//        return maintCommentsRepository.findAll()
//                .stream()
//                .map(m -> new MaintCommentsDto(m.getMaint().getMaintIdentifier(), m.getCommentText()))
//                .collect(toList());
//
//    }

    // Much better approach to return DTO
    public List<MaintCommentsMaintIdentifierDto> getIdentifiedMaintComments() {
        return maintCommentsRepository.findMaintIdentifiedComments();
    }

    public MaintComments getMaintComment(Long maintCommentId) {
        return maintCommentsRepository
                .findById(maintCommentId)
                .orElseThrow(() -> new NoSuchMaintCommentsException(maintCommentId));
    }

    public ResponseEntity<CreateMaintCommentResponseDto> createComment(MaintComments maintComment) {
        final var commentId = maintCommentsRepository
                .save(maintComment)
                .getId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintCommentResponseDto.builder()
                        .maintCommentId(commentId)
                        .build());
    }
}
