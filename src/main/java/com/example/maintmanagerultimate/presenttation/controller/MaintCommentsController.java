package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.services.MaintCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class MaintCommentsController {

    private final MaintCommentsRepository commentsRepository;
    private final MaintCommentsService maintCommentsService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody MaintComments maintComment) {
        return maintCommentsService.createComment(maintComment);
    }

    @GetMapping
    public ResponseEntity<GetMaintCommentsResponseDto> getComment(@RequestParam Long maintCommentId) {
        return maintCommentsService.getMaintComment(maintCommentId);
    }

    // Use the approach in the case we need to work with pagination
    @GetMapping("/all/pageable")
    public ResponseEntity<Page<GetMaintCommentsResponseDto>> getMaintCommentsPageable(Pageable pageable) {
        return maintCommentsService.getMaintComments(pageable);
    }

    @GetMapping("/all/identified")
    public ResponseEntity<List<MaintCommentsMaintIdentifierDto>> getIdentifiedMaintComments() {
        return maintCommentsService.getIdentifiedMaintComments();
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetMaintCommentsResponseDto>> getComments() {
        return maintCommentsService.getComments();
    }

    //Just an example of different usage
    @GetMapping("/all/by")
    public ResponseEntity<List<GetMaintCommentsResponseDto>> getAllComments() {
        return maintCommentsService.getAllComments();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {
        return maintCommentsService.deleteMaintComment(commentId);
    }
}
