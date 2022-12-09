package com.example.maintmanagerultimate.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.presenttation.controller.MaintCommentsController;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class MaintCommentsTest extends MaintManagerUltimateApplicationTests {

    @Mock
    private MaintCommentsController maintCommentsController;

    private static Maint maint;

    @BeforeAll
    public static void createMaint() {
        maint = Maint.builder()
                .id(1L)
                .maintIdentifier("MAINT-1.1.2")
                .capabilityId(1L)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(1)
                .fixVersion("1.1.1")
                .client("AIB")
                .build();
    }

    @Test
    void testMaintCommentShouldBeCreated() {
        final var maintCommentRequest = MaintComments.builder()
                .maint(maint)
                .commentText("New comment")
                .createdData(now())
                .build();

        ResponseEntity response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintCommentResponseDto.builder()
                        .maintCommentId(1L)
                        .build());

        when(maintCommentsController.createComment(maintCommentRequest)).thenReturn(response);

        assertThat(maintCommentsController.createComment(maintCommentRequest)).isEqualTo(response);
    }

    @Test
    void testMaintCommentShouldBeRetrieved() {
        ResponseEntity<GetMaintCommentsResponseDto> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(GetMaintCommentsResponseDto.builder()
                        .id(1L)
                        .createdData(now())
                        .commentText("One more comment")
                        .build());

        when(maintCommentsController.getComment(1L)).thenReturn(response);

        assertThat(maintCommentsController.getComment(1L)).isEqualTo(response);
    }

    @Test
    void testAllMaintCommentsShouldBeRetrieved() {
        ResponseEntity<List<GetMaintCommentsResponseDto>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(List.of(GetMaintCommentsResponseDto.builder()
                        .id(1L)
                        .createdData(now())
                        .commentText("Another comment")
                        .build()));

        when(maintCommentsController.getComments()).thenReturn(response);

        assertThat(maintCommentsController.getComments()).isEqualTo(response);
    }
}
