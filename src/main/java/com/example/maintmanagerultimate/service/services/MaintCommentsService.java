package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintToDeleteException;
import com.example.maintmanagerultimate.service.exeptions.maint_comments.NoSuchMaintCommentsException;
import com.example.maintmanagerultimate.service.exeptions.maint_comments.NoSuchMaintCommentsToDeleteException;
import com.example.maintmanagerultimate.service.mappers.MaintCommentsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<MaintCommentsMaintIdentifierDto>> getIdentifiedMaintComments() {
        final var comments = maintCommentsRepository.findMaintIdentifiedComments();

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    public ResponseEntity<GetMaintCommentsResponseDto> getMaintComment(Long maintCommentId) {
        final var maintComment = maintCommentsRepository
                .findById(maintCommentId)
                .orElseThrow(() -> new NoSuchMaintCommentsException(maintCommentId));

        final var mappedMaintComment = MaintCommentsMapper.INSTANCE.maintCommentsEntityToMaintCommentsDto(maintComment);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaintComment);
    }

    public ResponseEntity<?> createComment(MaintComments maintComment) {
        final Long commentId;

        try {
            commentId = maintCommentsRepository
                    .save(maintComment)
                    .getId();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseErrorDto.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDate.now())
                            .comment("Could not create Maint comment"));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintCommentResponseDto.builder()
                        .maintCommentId(commentId)
                        .build());
    }

    public ResponseEntity<Page<GetMaintCommentsResponseDto>> getMaintComments(Pageable pageable) {
        final var comments = maintCommentsRepository.findAll(pageable);

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        final var mappedMaintComments = comments.stream()
                .map(MaintCommentsMapper.INSTANCE::maintCommentsEntityToMaintCommentsDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageImpl<>(mappedMaintComments));
    }

    public ResponseEntity<List<GetMaintCommentsResponseDto>> getComments() {
        final var comments = maintCommentsRepository.findAll();

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        final var mappedMaintComments = comments.stream()
                .map(MaintCommentsMapper.INSTANCE::maintCommentsEntityToMaintCommentsDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaintComments);
    }

    public ResponseEntity<List<GetMaintCommentsResponseDto>> getAllComments() {
        final var comments = maintCommentsRepository.findAllBy(MaintComments.class);

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        final var mappedMaintComments = comments.stream()
                .map(MaintCommentsMapper.INSTANCE::maintCommentsEntityToMaintCommentsDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mappedMaintComments);
    }

    public ResponseEntity<HttpStatus> deleteMaintComment(Long maintCommentId) {
        try {
            maintCommentsRepository.deleteById(maintCommentId);
        }catch (Exception e){
            throw new NoSuchMaintCommentsToDeleteException(maintCommentId);
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
