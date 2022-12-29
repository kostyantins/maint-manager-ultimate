package com.example.maintmanagerultimate.integration.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
import com.example.maintmanagerultimate.service.dto.FixVersionRequestDto;
import com.example.maintmanagerultimate.service.dto.GetMaintResponseDto;
import com.example.maintmanagerultimate.service.dto.UpdateMaintDto;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class MaintIntegrationTest extends MaintManagerUltimateApplicationTests {

    private static final String MAINT_BODY_SHOULD_NOT_BE_NULL = "Maint body should not be null !!";
    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    @Autowired
    private MaintController maintService;
    @Autowired
    private MaintRepository maintRepository;
    @Autowired
    private MaintCommentsRepository maintCommentsRepository;

    @Test
    void testMaintShouldBeCreated() {
        final var expectedMaint = createMaint();

        final var actualMaintId = requireNonNull(maintRepository.findById(expectedMaint.getId())
                .orElseThrow(() -> new NoSuchMaintException(expectedMaint.getId())))
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaint.getId());
    }

    @Test
    void testMaintShouldBeRetrievedById() {
        final var maint = createMaint();

        final var actualMaintId = requireNonNull(maintService.getMaint(maint.getId())
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getId();

        assertThat(actualMaintId).isEqualTo(maint.getId());
    }

    @Test
    void testMaintShouldBeRetrievedByIdIdentifier() {
        final var maint = createMaint();

        final var actualMaintId = requireNonNull(maintService.getMaintByIdIdentifier(maint.getMaintIdentifier())
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getId();

        assertThat(actualMaintId).isEqualTo(maint.getId());
    }

    @Test
    void testMaintShouldBeDeleted() {
        final var maint = createMaint();

        maintService.deleteMaint(maint.getId());

        assertThat(maintRepository.findById(maint.getId())).isEmpty();
    }

    @Test
    void testMaintsShouldBeRetrieved() {
        final var maint_01 = createMaint();
        final var maint_02 = createMaint();

        final var maints = maintService.getMaints().getBody();

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

        maintService.patchMaintFixVersion(patchedMaintBody);

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
                .createdData(maint.getCreatedDate())
                .dueData(now().plusMonths(5))
                .solvePriorityId(maint.getSolvePriorityId())
                .fixVersion(FAKER.number().digits(5))
                .client(maint.getClient())
                .build();

        maintService.updateMaint(updatedMaintBody);

        final var updatedMaint = maintRepository.findById(maint.getId()).orElse(null);

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(requireNonNull(updatedMaint, "Updated maint should not be null !!").getFixVersion())
                    .as("Fix version")
                    .isEqualTo(updatedMaintBody.getFixVersion());
            softAssertions.assertThat(updatedMaint.getDueData())
                    .as("Due data")
                    .isEqualTo(updatedMaintBody.getDueData());
        });
    }

    private Maint createMaint() {
        final var maintRequest = Maint.builder()
                .maintIdentifier(format("MAINT-%s", FAKER.number().digits(10)))
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueData(now())
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
