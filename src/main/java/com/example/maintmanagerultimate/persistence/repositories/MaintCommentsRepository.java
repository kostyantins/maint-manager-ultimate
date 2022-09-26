package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.service.dto.MaintCommentsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaintCommentsRepository extends JpaRepository<MaintComments, Long> {

    // todo it seems it doesn't work because of
    @Query("select new com.example.maintmanagerultimate.service.dto.MaintCommentsDto(m.maintIdentifier, c.commentText) from Maint m inner join m.comments c")
    //@Query("select new com.example.maintmanagerultimate.service.dto.MaintCommentsDto(m.maintIdentifier, c.commentText) from Maint m inner join MaintComments c on m.id = c.maint.id")
    List<MaintCommentsDto> findMaintIdentifiedComments();
}
