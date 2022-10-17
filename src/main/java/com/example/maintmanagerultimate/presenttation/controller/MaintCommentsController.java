package com.example.maintmanagerultimate.presenttation.controller;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
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
    public ResponseEntity<CreateMaintCommentResponseDto> createComment(@RequestBody MaintComments maintComment) {
        return maintCommentsService.createComment(maintComment);
    }

    @GetMapping
    public MaintComments getComment(@RequestParam Long maintCommentId) {
        return maintCommentsService.getMaintComment(maintCommentId);
    }

    // Use the approach in the case we need to work with pagination
    @GetMapping("/all/pageable")
    public Page<MaintComments> getMaintCommentsPageable(Pageable pageable) {
        return commentsRepository.findAll(pageable);
    }

    @GetMapping("/all/identified")
    public List<MaintCommentsMaintIdentifierDto> getIdentifiedMaintComments() {
        return maintCommentsService.getIdentifiedMaintComments();
    }

    @GetMapping("/all")
    public List<MaintComments> getComments() {
        return commentsRepository.findAll();
    }

    //Just an example of different usage
    @GetMapping("/all/by")
    public List<MaintComments> getAllComments() {
        return commentsRepository.findAllBy(MaintComments.class);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentsRepository.deleteById(commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
