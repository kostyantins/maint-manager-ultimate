package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.presenttation.swagger.MaintCommentsSwagger;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.services.MaintCommentsService;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class MaintCommentsController implements MaintCommentsSwagger {

    private final MaintCommentsService maintCommentsService;

    @PostMapping
    @Override
    public ResponseEntity<?> createComment(@RequestBody MaintComments maintComment) {
        final CreateMaintCommentResponseDto commentComment;

        try {
            commentComment = maintCommentsService.createComment(maintComment);
        } catch (IllegalReceiveException e) {
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
                        .maintCommentId(commentComment.getMaintCommentId())
                        .build());
    }

    @GetMapping
    @Override
    public ResponseEntity<GetMaintCommentsResponseDto> getComment(@RequestParam Long maintCommentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintCommentsService.getMaintComment(maintCommentId));
    }

    // Use the approach in the case we need to work with pagination
    @GetMapping("/pageable")
    @Override
    public ResponseEntity<Page<GetMaintCommentsResponseDto>> getMaintCommentsPageable(Pageable pageable) {
        final var comments = maintCommentsService.getMaintComments(pageable);

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @GetMapping("/identified")
    @Override
    public ResponseEntity<List<MaintCommentsMaintIdentifierDto>> getIdentifiedMaintComments() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintCommentsService.getIdentifiedMaintComments());
    }

    //todo remove '/all' mapping through replace query param with path param into getComment()
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<GetMaintCommentsResponseDto>> getComments() {
        final var comments = maintCommentsService.getComments();

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    //Just an example of different usage
    @GetMapping("/by")
    @Override
    public ResponseEntity<List<GetMaintCommentsResponseDto>> getAllComments() {
        final var comments = maintCommentsService.getAllComments();

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    @DeleteMapping("/{commentId}")
    @Override
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {
        maintCommentsService.deleteMaintComment(commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
