package com.example.maintmanagerultimate.integration.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class MaintIntegrationTest extends MaintManagerUltimateApplicationTests {

    @Autowired
    private MaintController maintController;

    @Autowired
    private MaintRepository maintRepository;

    @Autowired
    private MaintCommentsRepository maintCommentsRepository;

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

        final var expectedMaint = testRestTemplate.postForEntity(
                "http://localhost:8080/maints",
                maintRequest,
                CreateMaintResponseDto.class);

        final var actualMaintId = requireNonNull(maintRepository.findById(expectedMaint.getBody().getMaintId())
                .orElseThrow(() -> new NoSuchMaintException(expectedMaint.getBody().getMaintId())))
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaint.getBody().getMaintId());
    }

    @Test
    void testMaintShouldBeRetrievedById() {
        final var maint = createMaint();

        final var actualMaintId = requireNonNull(maintController.getMaint(maint.getId())
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getId();

        assertThat(actualMaintId).isEqualTo(maint.getId());
    }

    @Test
    void testMaintShouldBeRetrievedByIdIdentifier() {
        final var maint = createMaint();

        final var actualMaintId = requireNonNull(maintController.getMaintByIdIdentifier(maint.getMaintIdentifier())
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getId();

        assertThat(actualMaintId).isEqualTo(maint.getId());
    }

    @Test
    void testMaintShouldBeDeleted() {
        final var maint = createMaint();

        maintController.deleteMaint(maint.getId());

        assertThat(maintRepository.findById(maint.getId())).isEmpty();
    }

    @Test
    void testMaintsShouldBeRetrieved() {
        final var maint_01 = createMaint();
        final var maint_02 = createMaint();

        final var maints = maintController.getMaints().getBody();

        final var maintsIds = requireNonNull(maints, MAINT_BODY_SHOULD_NOT_BE_NULL).stream()
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

        maintController.patchMaintFixVersion(patchedMaintBody);

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

        maintController.updateMaint(updatedMaintBody);

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

        return maint;
    }
}
