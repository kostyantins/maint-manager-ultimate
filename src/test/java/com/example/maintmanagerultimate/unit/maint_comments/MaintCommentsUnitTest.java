package com.example.maintmanagerultimate.unit.maint_comments;

import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentsRequestDto;
import com.example.maintmanagerultimate.service.mappers.MaintCommentsMapper;
import com.example.maintmanagerultimate.service.services.MaintCommentsService;
import com.example.maintmanagerultimate.unit.UnitTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaintCommentsUnitTest extends UnitTestBase {

    @Mock
    private MaintRepository maintRepository;

    @Mock
    private MaintCommentsRepository maintCommentsRepository;

    @InjectMocks
    private MaintCommentsService maintCommentsService;

    @Spy
    private MaintCommentsMapper maintCommentsMapper;

    @Test
    void testMaintCommentShouldBeCreated() {
        final var maintEntity = createMaintModel();

        final var maintCommentRequest = CreateMaintCommentsRequestDto.builder()
                .maint(createRequestMaintModel())
                .commentText(FAKER.lorem().sentence(10))
                .createdDate(now())
                .build();

        final var maintCommentEntity = MaintComments.builder()
                .id(1L)
                .maint(maintEntity)
                .commentText(FAKER.lorem().sentence(10))
                .createdDate(now())
                .build();

        final var response = CreateMaintCommentResponseDto.builder()
                .maintCommentId(1L)
                .build();

        when(maintCommentsRepository.save(maintCommentEntity)).thenReturn(maintCommentEntity);

        //todo doesnt work, understand why
        assertThat(maintCommentsService.createComment(maintCommentRequest)).isEqualTo(response);
    }

    @Test
    void testMaintCommentShouldBeRetrieved() {
        final var maintCommentEntity = MaintComments.builder()
                .id(1L)
                .maint(createMaintModel())
                .commentText(FAKER.lorem().sentence(10))
                .createdDate(now())
                .build();

        when(maintCommentsRepository.findById(1L)).thenReturn(Optional.of(maintCommentEntity));

        assertThat(maintCommentsService.getMaintComment(1L)).isEqualTo(maintCommentsMapper.maintCommentsEntityToMaintCommentsDto(maintCommentEntity));
    }

    @Test
    void testAllMaintCommentsShouldBeRetrieved() {
        final var maintCommentEntity = List.of(MaintComments.builder()
                .id(1L)
                .maint(createMaintModel())
                .commentText(FAKER.lorem().sentence(10))
                .createdDate(now())
                .build());

        when(maintCommentsRepository.findAll()).thenReturn(maintCommentEntity);

        final var expectedComments = maintCommentEntity.stream()
                .map(maintCommentsMapper::maintCommentsEntityToMaintCommentsDto)
                .toList();

        assertThat(maintCommentsService.getComments()).isEqualTo(expectedComments);
    }
}
