package com.example.maintmanagerultimate.unit.maint_comments;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.service.dto.CreateMaintCommentResponseDto;
import com.example.maintmanagerultimate.service.dto.GetMaintCommentsResponseDto;
import com.example.maintmanagerultimate.service.services.MaintCommentsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaintCommentsUnitTest extends MaintManagerUltimateApplicationTests {

    @Mock
    private MaintCommentsService maintCommentsService;

    private static Maint maint;

    @BeforeAll
    public static void createMaint() {
        maint = Maint.builder()
                .id(1L)
                .maintIdentifier("MAINT-1.1.2")
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion("1.1.1")
                .client("AIB")
                .build();
    }

    @Test
    void testMaintCommentShouldBeCreated() {
        final var maintCommentRequest = MaintComments.builder()
                .maint(maint)
                .commentText("New comment")
                .createdDate(now())
                .build();

        final var response = CreateMaintCommentResponseDto.builder()
                .maintCommentId(1L)
                .build();

        when(maintCommentsService.createComment(maintCommentRequest)).thenReturn(response);

        assertThat(maintCommentsService.createComment(maintCommentRequest)).isEqualTo(response);
    }

    @Test
    void testMaintCommentShouldBeRetrieved() {
        final var response = GetMaintCommentsResponseDto.builder()
                .id(1L)
                .createdDate(now())
                .commentText("One more comment")
                .build();

        when(maintCommentsService.getMaintComment(1L)).thenReturn(response);

        assertThat(maintCommentsService.getMaintComment(1L)).isEqualTo(response);
    }

    @Test
    void testAllMaintCommentsShouldBeRetrieved() {
        List<GetMaintCommentsResponseDto> response = List.of(GetMaintCommentsResponseDto.builder()
                .id(1L)
                .createdDate(now())
                .commentText("Another comment")
                .build());

        when(maintCommentsService.getComments()).thenReturn(response);

        assertThat(maintCommentsService.getComments()).isEqualTo(response);
    }
}
