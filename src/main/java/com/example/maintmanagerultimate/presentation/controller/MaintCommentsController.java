package com.example.maintmanagerultimate.presentation.controller;

import com.example.maintmanagerultimate.presentation.interfaces.MaintCommentsInterface;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentsRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.services.MaintCommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CacheConfig(cacheNames = {"maintComments"})
@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class MaintCommentsController implements MaintCommentsInterface {

    private final MaintCommentsService maintCommentsService;

    @PostMapping
    @Override
    public ResponseEntity<CreateMaintCommentResponseDto> createComment(@RequestBody CreateMaintCommentsRequestDto maintComment) {
        final var commentComment = maintCommentsService.createComment(maintComment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintCommentResponseDto.builder()
                        .maintCommentId(commentComment.getMaintCommentId())
                        .build());
    }

    @GetMapping("/{maintCommentId}")
    @Override
    public ResponseEntity<GetMaintCommentsResponseDto> getComment(@PathVariable Long maintCommentId) {
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

    @Cacheable //todo replace on service layer
    @GetMapping
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
