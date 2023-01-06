package com.example.maintmanagerultimate.unit.maint;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import com.example.maintmanagerultimate.service.mappers.MaintMapper;
import com.example.maintmanagerultimate.service.services.MaintService;
import com.example.maintmanagerultimate.unit.UnitTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaintUnitTest extends UnitTestBase {

    @Mock
    private MaintRepository maintRepository;

    @Mock
    private MaintCommentsRepository maintCommentsRepository;

    @InjectMocks
    private MaintService maintService;

    @Spy
    private MaintMapper mapper;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var maintResponse = Maint.builder()
                .id(1L)
                .build();

        when(maintRepository.save(mapper.createMaintDtoToMaintEntity(maintRequest))).thenReturn(maintResponse);
        when(maintCommentsRepository.saveAll(maintResponse.getComments())).thenReturn(Collections.emptyList());

        final var actualMaint = maintService.createMaint(maintRequest);

        assertThat(actualMaint.getMaintId()).isEqualTo(maintResponse.getId());
    }

    @Test
    void testMaintCommentsShouldBeFetched() {
        final var maintResponse = createMaintModel();

        when(maintRepository.findByIdFetchComment(1L)).thenReturn(maintResponse);

        assertThat(maintService.getMaintFetchComment(1L)).isEqualTo(mapper.maintEntityToMaintDto(maintResponse));
    }

    @Test
    void testMaintsShouldBeRetrieved() {
        final var maintResponse = List.of(createMaintModel());

        when(maintRepository.findAll()).thenReturn(maintResponse);

        final var expected = maintResponse.stream()
                .map(i -> mapper.maintEntityToMaintDto(i))
                .toList();

        assertThat(maintService.getMaints()).isEqualTo(expected);
    }

    @Test
    void testMaintShouldBeRetrievedByIdIdentifier() {
        final var maintEntity = createMaintModel();

        when(maintRepository.findMaintByMaintIdentifier(maintEntity.getMaintIdentifier())).thenReturn(maintEntity);

        assertThat(maintService.getMaintByIdIdentifier(maintEntity.getMaintIdentifier())).isEqualTo(mapper.maintEntityToMaintDto(maintEntity));
    }

    @Test
    void testMaintShouldBeDeleted() {
        maintService.deleteMaint(1L);

        verify(maintRepository).deleteById(1L);
    }

    @Test
    void testMaintFixVersionShouldBePatched() {
        final var maintEntity = createMaintModel();

        maintEntity.setFixVersion(MAINT_FIX_VERSION);

        final var patchedMaint = FixVersionRequestDto.builder()
                .maintId(1L)
                .fixVersion(MAINT_FIX_VERSION)
                .build();

        when(maintRepository.findByIdFetchComment(1L)).thenReturn(maintEntity);
        when(maintRepository.save(maintEntity)).thenReturn(maintEntity);
        when(maintCommentsRepository.saveAll(maintEntity.getComments())).thenReturn(maintEntity.getComments());

        maintService.patchMaintFixVersion(patchedMaint);

        verify(maintRepository).findByIdFetchComment(1L);
        verify(maintRepository).save(maintEntity);
        verify(maintCommentsRepository).saveAll(maintEntity.getComments());
    }

    @Test
    void testMaintShouldBeUpdated() {
        final var maintEntity = createMaintModel();

        final var update = UpdateMaintDto.builder()
                .id(maintEntity.getId())
                .maintIdentifier(maintEntity.getMaintIdentifier())
                .capabilityId(maintEntity.getCapabilityId())
                .createdDate(maintEntity.getCreatedDate())
                .dueDate(maintEntity.getDueDate())
                .solvePriorityId(maintEntity.getSolvePriorityId())
                .fixVersion(maintEntity.getFixVersion())
                .client(maintEntity.getClient())
                .build();

        when(maintRepository.getReferenceById(1L)).thenReturn(maintEntity);
        when(maintRepository.save(maintEntity)).thenReturn(maintEntity);

        maintService.updateMaint(update);
    }
}
