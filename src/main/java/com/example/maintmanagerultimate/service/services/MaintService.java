package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintService {

    public void createCommentIfPresent(Maint createdMaint){
        if (createdMaint.getComments() != null && !createdMaint.getComments().isEmpty()) {
//            createdMaint.getComments().forEach(i -> {
//                createdMaint.addComment(new MaintComments(i.getCommentText(), createdMaint));
//                log.debug("The comment: '{}' was added to the maint: {}",
//                        i.getCommentText(), createdMaint.getId());
//            });

            createdMaint.getComments().stream()
                    .map(MaintComments::getCommentText)
                    .collect(Collectors.toList())
                    .forEach(i -> new MaintComments(i, createdMaint));
        }
    }
}
