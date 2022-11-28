package com.example.maintmanagerultimate.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import com.example.maintmanagerultimate.service.services.MaintService;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MaintTest extends MaintManagerUltimateApplicationTests {

    @Mock
    MaintService maintService;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var maintResponse = CreateMaintResponseDto.builder()
                .maintId(1L)
                .build();

        when(maintService.createMaintAndCommentsIfPresent(maintRequest)).thenReturn(maintResponse);

        assertThat(maintService.createMaintAndCommentsIfPresent(maintRequest).getMaintId()).isEqualTo(maintResponse.getMaintId());
    }

    @Test
    void testMainsShouldBeRetrieved() {
        final var maintResponse = createResponseMaintModel();

        when(maintService.getMaints()).thenReturn(List.of(maintResponse));

        assertThat(maintService.getMaints().get(0)).isEqualTo(maintResponse);
    }

    @Test
    void testMainCommentsShouldBeFetched() {
        final var maintResponse = createResponseMaintModel();

        when(maintService.getMaintFetchComment(1L)).thenReturn(maintResponse);

        assertThat(maintService.getMaintFetchComment(1L)).isEqualTo(maintResponse);
    }

    @Test
    void testMainShouldBeRetrievedByIdIdentifier() {
        final var maintResponse = createResponseMaintModel();

        when(maintService.getMaintByIdIdentifier(maintResponse.getMaintIdentifier())).thenReturn(maintResponse);

        assertThat(maintService.getMaintByIdIdentifier(maintResponse.getMaintIdentifier())).isEqualTo(maintResponse);
    }

    //todo how to properly test void methods
    @Test
    void testMainShouldBeDeleted() {
//        MaintService maintService = mock(MaintService.class);
//
//        doAnswer(() -> 204)
//                .when(maintService.deleteMaint(1L));
//
//        assertThat(maintService.deleteMaint(1l).isEqualTo(204);
    }

    //todo how to properly test void methods
    @Test
    void testMainFixVersionShouldBePatched() {
//        final var patch = FixVersionRequestDto.builder()
//                .maintId(1L)
//                .fixVersion("2.2.2")
//                .build();
//
//        when(maintService.patchMaintFixVersion(patch)).then(answer);
//
//        assertThat(maintService.patchMaintFixVersion(patch).isEqualTo(200);
    }

    //todo how to properly test void methods
    @Test
    void testMainShouldBeUpdated() {
        final var update = UpdateMaintDto.builder()
                .id(1L)
                .maintIdentifier("MAINT-1.1")
                .capabilityId(1L)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(2)
                .fixVersion("3.3.3")
                .client("MCB")
                .build();
//
//        when(maintService.updateMaint(update)).then(200);
//
//        assertThat(maintService.deleteMaint(1l).isEqualTo(204);
    }

    private Maint createRequestMaintModel() {
        return Maint.builder()
                .maintIdentifier("MAINT-1")
                .capabilityId(1L)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(1)
                .fixVersion("1.1.1")
                .client("MCB")
                .build();
    }

    private GetMaintResponseDto createResponseMaintModel() {
        return GetMaintResponseDto.builder()
                .id(1L)
                .maintIdentifier("MAINT-1")
                .capabilityId(1L)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(1)
                .fixVersion("1.1.1")
                .client("MCB")
                .build();
    }
}
