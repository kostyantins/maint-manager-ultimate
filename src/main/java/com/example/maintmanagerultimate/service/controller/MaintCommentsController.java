package com.example.maintmanagerultimate.service.controller;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.exeptions.NoMaintCommentsException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class MaintCommentsController {

    private final MaintCommentsRepository commentsRepository;

    @PostMapping("/create")
    public Long getComment(@RequestBody MaintComments maintComment){
        return commentsRepository
                .save(maintComment)
                .getId();
    }

    @GetMapping("/get")
    public MaintComments getComment(@RequestParam Long maintCommentId){
        return commentsRepository
                .findById(maintCommentId)
                .orElseThrow(() -> new NoMaintCommentsException(maintCommentId));
    }

    @GetMapping("/get/all")
    public List<MaintComments> getComments(){
        return commentsRepository.findAll();
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathParam("commentId") Long commentId){
        commentsRepository.deleteById(commentId);
    }
}
