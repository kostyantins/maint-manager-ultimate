package com.example.maintmanagerultimate.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class MaintTest extends MaintManagerUltimateApplicationTests {

    @Mock
    MaintController maintController;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var maintResponse = CreateMaintResponseDto.builder()
                .maintId(1L)
                .build();

        ResponseEntity<CreateMaintResponseDto> response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateMaintResponseDto.builder()
                        .maintId(maintResponse.getMaintId())
                        .build());

        when(maintController.createMaint(maintRequest)).thenReturn(response);

        assertThat(maintController.createMaint(maintRequest)).isEqualTo(response);
    }

    @Test
    void testMainsShouldBeRetrieved() {
        final var maintResponse = List.of(createResponseMaintModel());

        ResponseEntity<List<GetMaintResponseDto>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(maintResponse);

        when(maintController.getMaints()).thenReturn(response);

        assertThat(maintController.getMaints()).isEqualTo(response);
    }

    @Test
    void testMainsShouldNotBeRetrieved() {
        ResponseEntity<List<GetMaintResponseDto>> response = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

        when(maintController.getMaints()).thenReturn(response);

        assertThat(maintController.getMaints()).isEqualTo(response);
    }

    @Test
    void testMainCommentsShouldBeFetched() {
        final var maintResponse = createResponseMaintModel();

        ResponseEntity<GetMaintResponseDto> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(maintResponse);

        when(maintController.getMaint(1L)).thenReturn(response);

        assertThat(maintController.getMaint(1L)).isEqualTo(response);
    }

    @Test
    void testMainShouldBeRetrievedByIdIdentifier() {
        final var maintResponse = createResponseMaintModel();

        ResponseEntity<GetMaintResponseDto> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(maintResponse);

        when(maintController.getMaintByIdIdentifier(maintResponse.getMaintIdentifier())).thenReturn(response);

        assertThat(maintController.getMaintByIdIdentifier(maintResponse.getMaintIdentifier())).isEqualTo(response);
    }

    @Test
    void testMainShouldBeDeleted() {
        ResponseEntity<HttpStatus> response = ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

        when(maintController.deleteMaint(1L)).thenReturn(response);

        assertThat(maintController.deleteMaint(1L)).isEqualTo(response);
    }

    @Test
    void testMainFixVersionShouldBePatched() {
        final var patch = FixVersionRequestDto.builder()
                .maintId(1L)
                .fixVersion("2.2.2")
                .build();

        ResponseEntity<HttpStatus> response = ResponseEntity
                .status(HttpStatus.OK)
                .build();

        when(maintController.patchMaintFixVersion(patch)).thenReturn(response);

        assertThat(maintController.patchMaintFixVersion(patch)).isEqualTo(response);
    }

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

        ResponseEntity<HttpStatus> response = ResponseEntity
                .status(HttpStatus.OK)
                .build();

        when(maintController.updateMaint(update)).thenReturn(response);

        assertThat(maintController.updateMaint(update)).isEqualTo(response);
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
