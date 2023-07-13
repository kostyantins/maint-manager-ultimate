package com.example.maintmanagerultimate.service.services;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentsRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.dto.MaintCommentsMaintIdentifierDto;
import com.example.maintmanagerultimate.service.exceptions.maint_comments.NoSuchMaintCommentsException;
import com.example.maintmanagerultimate.service.exceptions.maint_comments.NoSuchMaintCommentsToDeleteException;
import com.example.maintmanagerultimate.service.mappers.MaintCommentsMapper;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MaintCommentsService {

    private final MaintCommentsRepository maintCommentsRepository;

    @Autowired
    private final MaintCommentsMapper maintCommentsMapper;

    // Not good implementation causing a plenty additional select calls from result set
//    public List<MaintCommentsDto> getIdentifiedMaintComments() {
//        return maintCommentsRepository.findAll()
//                .stream()
//                .map(m -> new MaintCommentsDto(m.getMaint().getMaintIdentifier(), m.getCommentText()))
//                .collect(toList());
//
//    }

    // Much better approach to return DTO
    public List<MaintCommentsMaintIdentifierDto> getIdentifiedMaintComments() {
        return maintCommentsRepository.findMaintIdentifiedComments();
    }

    public GetMaintCommentsResponseDto getMaintComment(Long maintCommentId) {
        final var maintComment = maintCommentsRepository
                .findById(maintCommentId)
                .orElseThrow(() -> new NoSuchMaintCommentsException(maintCommentId));

        return maintCommentsMapper.maintCommentsEntityToMaintCommentsDto(maintComment);
    }

    public CreateMaintCommentResponseDto createComment(CreateMaintCommentsRequestDto maintComment) {
        final Long commentId;

        final var maintCommentEntity = maintCommentsMapper.createMaintCommentsDtoToMaintCommentsEntity(maintComment);

        try {
            commentId = maintCommentsRepository
                    .save(maintCommentEntity)
                    .getId();
        } catch (Exception e) {
            throw new IllegalReceiveException(format("Cannot create Maint comment \n %s \n %s", e.getMessage(), e.getCause()));
        }

        return CreateMaintCommentResponseDto.builder()
                .maintCommentId(commentId)
                .build();
    }

    public Page<GetMaintCommentsResponseDto> getMaintComments(Pageable pageable) {
        final var comments = maintCommentsRepository.findAll(pageable);

        return new PageImpl<>(comments.stream()
                .map(maintCommentsMapper::maintCommentsEntityToMaintCommentsDto)
                .toList());
    }

    public List<GetMaintCommentsResponseDto> getComments() {
        final var comments = maintCommentsRepository.findAll();

        return comments.stream()
                .map(maintCommentsMapper::maintCommentsEntityToMaintCommentsDto)
                .toList();
    }

    public List<GetMaintCommentsResponseDto> getAllComments() {
        final var comments = maintCommentsRepository.findAllBy(MaintComments.class);

        return comments.stream()
                .map(maintCommentsMapper::maintCommentsEntityToMaintCommentsDto)
                .toList();
    }

    public void deleteMaintComment(Long maintCommentId) {
        try {
            maintCommentsRepository.deleteById(maintCommentId);
        } catch (Exception e) {
            throw new NoSuchMaintCommentsToDeleteException(maintCommentId);
        }
    }
}
