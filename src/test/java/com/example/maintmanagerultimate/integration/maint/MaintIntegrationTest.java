package com.example.maintmanagerultimate.integration.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.mappers.MaintMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.List;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class MaintIntegrationTest extends MaintManagerUltimateApplicationTests {

    @Autowired
    private MaintRepository maintRepository;

    @Autowired
    private MaintCommentsRepository maintCommentsRepository;

    @Autowired
    private MaintMapper maintMapper;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = CreateMaintRequestDto.builder()
                .maintIdentifier(format("MAINT-%s", FAKER.number().digits(10)))
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueDate(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit()))
                .client(FAKER.company().name())
                .build();

        final var expectedMaint = restTemplate.postForEntity(
                absoluteUrl("/maints"),
                maintRequest,
                CreateMaintResponseDto.class);

        final var actualMaintId = maintRepository.findById(requireNonNull(expectedMaint.getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL).getMaintId())
                .orElseThrow(() -> new NoSuchMaintException(expectedMaint.getBody().getMaintId()))
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaint.getBody().getMaintId());
    }

    @Test
    void testMaintShouldBeRetrievedById() {
        final var maint = createMaint();

        final var actualMaintId = restTemplate.getForEntity(
                absoluteUrl(format("/maints/%s", maint.getId())),
                GetMaintResponseDto.class);

        assertThat(requireNonNull(actualMaintId.getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL).getId()).isEqualTo(maint.getId());
    }

    @Test
    void testMaintShouldBeRetrievedByIdIdentifier() {
        final var maint = createMaint();

        final var actualMaint = restTemplate.getForObject(
                absoluteUrl("/maints/identifier?maintIdentifier={maintIdentifier}"),
                GetMaintResponseDto.class,
                maint.getMaintIdentifier());

        assertThat(actualMaint).isEqualTo(maintMapper.maintEntityToMaintDto(maint));
    }

    @Test
    void testMaintShouldBeDeleted() {
        final var maint = createMaint();

        restTemplate.delete(absoluteUrl(format("/maints/%s", maint.getId())));

        assertThat(maintRepository.findById(maint.getId())).isEmpty();
    }

    @Test
    void testMaintsShouldBeRetrieved() {
        final var maint_01 = createMaint();
        final var maint_02 = createMaint();

        final var maints = List.of(requireNonNull(restTemplate.getForEntity(
                absoluteUrl("/maints"),
                GetMaintResponseDto[].class).getBody(), "Maints array should not be null !!"));

        final var maintsIds = maints.stream()
                .map(GetMaintResponseDto::getId)
                .toList();

        assertThat(maintsIds).contains(maint_01.getId(), maint_02.getId());
    }

    @Test
    void testMaintFixVersionShouldBePatched() {
        final var maint = createMaint();

        final var patchedMaintBody = FixVersionRequestDto.builder()
                .maintId(maint.getId())
                .fixVersion(FAKER.number().digits(3))
                .build();

        final var requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

        restTemplate.patchForObject(
                absoluteUrl("/maints/fixversion"),
                patchedMaintBody,
                HttpStatus.class);

        final var patchedMaintFixVersion = requireNonNull(maintRepository.findById(maint.getId()).orElse(null),
                "Patched maint should not be null !!")
                .getFixVersion();

        assertThat(patchedMaintFixVersion).isEqualTo(patchedMaintBody.getFixVersion());
    }

    @Test
    void testMaintShouldBeUpdated() {
        final var maint = createMaint();

        final var updatedMaintBody = UpdateMaintDto.builder()
                .id(maint.getId())
                .maintIdentifier(maint.getMaintIdentifier())
                .capabilityId(maint.getCapabilityId())
                .createdDate(maint.getCreatedDate())
                .dueDate(now().plusMonths(5))
                .solvePriorityId(maint.getSolvePriorityId())
                .fixVersion(FAKER.number().digits(5))
                .client(maint.getClient())
                .build();

        restTemplate.put(
                absoluteUrl("/maints"),
                updatedMaintBody,
                HttpStatus.class);

        final var updatedMaint = maintRepository.findById(maint.getId()).orElse(null);

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(requireNonNull(updatedMaint, "Updated maint should not be null !!").getFixVersion())
                    .as("Fix version")
                    .isEqualTo(updatedMaintBody.getFixVersion());
            softAssertions.assertThat(updatedMaint.getDueDate())
                    .as("Due data")
                    .isEqualTo(updatedMaintBody.getDueDate());
        });
    }

    private Maint createMaint() {
        final var maintRequest = Maint.builder()
                .maintIdentifier(format("MAINT-%s", FAKER.number().digits(10)))
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueDate(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit()))
                .client(FAKER.company().name())
                .build();

        final var maint = maintRepository.save(maintRequest);

        final var comment = MaintComments.builder()
                .commentText(FAKER.lorem().word())
                .createdDate(now())
                .maint(maint)
                .build();

        maintCommentsRepository.save(comment);

        return Maint.builder()
                .id(maint.getId())
                .comments(List.of(comment))
                .maintIdentifier(maint.getMaintIdentifier())
                .capabilityId(maint.getCapabilityId())
                .createdDate(maint.getCreatedDate())
                .dueDate(maint.getDueDate())
                .solvePriorityId(maint.getSolvePriorityId())
                .fixVersion(maint.getFixVersion())
                .client(maint.getClient())
                .build();
    }
}
