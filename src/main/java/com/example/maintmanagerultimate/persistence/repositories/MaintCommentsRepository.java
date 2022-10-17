package com.example.maintmanagerultimate.persistence.repositories;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaintCommentsRepository extends JpaRepository<MaintComments, Long> {

    @Query("select new com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto(m.maintIdentifier, c.commentText) from Maint m inner join m.comments c")
    List<MaintCommentsMaintIdentifierDto> findMaintIdentifiedComments();

    //Just an example how we can work with abstract repo method
    <T>List<T> findAllBy(Class<T> classType);
}
