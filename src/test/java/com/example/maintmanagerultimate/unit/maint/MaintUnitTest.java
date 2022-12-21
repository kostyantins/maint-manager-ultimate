package com.example.maintmanagerultimate.unit.maint;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.service.dto.CreateMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import com.example.maintmanagerultimate.service.services.MaintService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintUnitTest {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);
    private static final String MAINT_IDENTIFIER = format("MAINT-%s", FAKER.number().digits(10));
    private static final String MAINT_FIX_VERSION = format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit());

    @Mock
    MaintService maintService;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var maintResponse = CreateMaintResponseDto.builder()
                .maintId(1L)
                .build();

        final var response = CreateMaintResponseDto.builder()
                .maintId(maintResponse.getMaintId())
                .build();

        when(maintService.createMaintAndCommentsIfPresent(maintRequest)).thenReturn(response);

        assertThat(maintService.createMaintAndCommentsIfPresent(maintRequest)).isEqualTo(response);
    }

    @Test
    void testMainsShouldBeRetrieved() {
        final var response = List.of(createResponseMaintModel());

        when(maintService.getMaints()).thenReturn(response);

        assertThat(maintService.getMaints()).isEqualTo(response);
    }

    @Test
    void testMainsShouldNotBeRetrieved() {
        List<GetMaintResponseDto> response = List.of();

        when(maintService.getMaints()).thenReturn(response);

        assertThat(maintService.getMaints()).isEqualTo(response);
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

    @Test
    void testMainShouldBeDeleted() {
        doNothing().when(maintService).deleteMaint(1L);

        maintService.deleteMaint(1L);

        verify(maintService).deleteMaint(1L);
    }

    @Test
    void testMainFixVersionShouldBePatched() {
        final var patch = FixVersionRequestDto.builder()
                .maintId(1L)
                .fixVersion(MAINT_FIX_VERSION)
                .build();

        doNothing().when(maintService).patchMaintFixVersion(patch);

        maintService .patchMaintFixVersion(patch);

        verify(maintService).patchMaintFixVersion(patch);
    }

    @Test
    void testMainShouldBeUpdated() {
        final var update = UpdateMaintDto.builder()
                .id(1L)
                .maintIdentifier(MAINT_IDENTIFIER)
                .capabilityId(Capabilities.APPROVALS)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(MAINT_FIX_VERSION)
                .client(FAKER.company().name())
                .build();

        doNothing().when(maintService).updateMaint(update);

        maintService.updateMaint(update);

        verify(maintService).updateMaint(update);
    }

    private Maint createRequestMaintModel() {
        return Maint.builder()
                .maintIdentifier(MAINT_IDENTIFIER)
                .capabilityId(Capabilities.APPROVALS)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(MAINT_FIX_VERSION)
                .client(FAKER.company().name())
                .build();
    }

    private GetMaintResponseDto createResponseMaintModel() {
        return GetMaintResponseDto.builder()
                .id(1L)
                .maintIdentifier(MAINT_IDENTIFIER)
                .capabilityId(Capabilities.APPROVALS)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(MAINT_FIX_VERSION)
                .client(FAKER.company().name())
                .build();
    }
}
