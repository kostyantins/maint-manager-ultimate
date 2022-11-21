package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
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
public class MaintCommentsController {

    private final MaintCommentsService maintCommentsService;

    @PostMapping
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
    public ResponseEntity<GetMaintCommentsResponseDto> getComment(@RequestParam Long maintCommentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintCommentsService.getMaintComment(maintCommentId));
    }

    // Use the approach in the case we need to work with pagination
    @GetMapping("/pageable")
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
    public ResponseEntity<List<MaintCommentsMaintIdentifierDto>> getIdentifiedMaintComments() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(maintCommentsService.getIdentifiedMaintComments());
    }

    //todo how wo solve bin conflicts conflicts
    @GetMapping("/all")
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
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {
       maintCommentsService.deleteMaintComment(commentId);

       return ResponseEntity
               .status(HttpStatus.NO_CONTENT)
               .build();
    }
}
