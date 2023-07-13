package com.example.maintmanagerultimate.integration.maint_comments;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintCommentsRepository;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.presentation.controller.MaintCommentsController;
import com.example.maintmanagerultimate.service.dto.*;
import com.example.maintmanagerultimate.service.exceptions.maint_comments.NoSuchMaintCommentsException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class MaintCommentsIntegrationTest extends MaintManagerUltimateApplicationTests {

    @Autowired
    private MaintCommentsController maintCommentsController;

    @Autowired
    private MaintRepository maintRepository;

    @Autowired
    private MaintCommentsRepository maintCommentsRepository;

    @Test
    void testMaintCommentShouldBeCreated() {
        final var createdMaint = createMaint();

        final var maintDtoRequest = CreateMaintRequestDto.builder()
                .id(createdMaint.getId())
                .maintIdentifier(createdMaint.getMaintIdentifier())
                .capabilityId(createdMaint.getCapabilityId())
                .createdDate(createdMaint.getCreatedDate())
                .dueDate(createdMaint.getDueDate())
                .solvePriorityId(createdMaint.getSolvePriorityId())
                .fixVersion(createdMaint.getFixVersion())
                .client(createdMaint.getClient())
                .build();

        final var maintCommentRequest = CreateMaintCommentsRequestDto.builder()
                .maint(maintDtoRequest)
                .commentText(FAKER.lorem().sentence(3))
                .createdDate(now())
                .build();

        final var maintComment = restTemplate.postForEntity(
                absoluteUrl("/comments"),
                maintCommentRequest,
                CreateMaintCommentResponseDto.class);

        final var expectedMaintCommentId = requireNonNull(maintComment.getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL).getMaintCommentId();

        final var actualMaintCommentId = requireNonNull(maintCommentsRepository.findById(expectedMaintCommentId)
                .orElseThrow(() -> new NoSuchMaintCommentsException(expectedMaintCommentId)))
                .getId();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(maintComment.getStatusCodeValue()).isEqualTo(SC_CREATED);
            softAssertions.assertThat(actualMaintCommentId).isEqualTo(expectedMaintCommentId);
        });
    }

    @Test
    void testMaintCommentShouldBeRetrieved() {
        final var createdMaint = createMaint();

        final var maintCommentEntityRequest = MaintComments.builder()
                .maint(createdMaint)
                .commentText(FAKER.lorem().sentence(2))
                .createdDate(now())
                .build();

        final var expectedMaintCommentId = maintCommentsRepository.save(maintCommentEntityRequest).getId();

        final var maintComment = restTemplate.getForEntity(
                absoluteUrl(format("/comments/%s", expectedMaintCommentId)),
                GetMaintCommentsResponseDto.class);

        final var actualMaintCommentId = requireNonNull(maintComment.getBody(), "Maint body should not be null !!")
                .getId();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(maintComment.getStatusCodeValue()).isEqualTo(SC_OK);
            softAssertions.assertThat(actualMaintCommentId).isEqualTo(expectedMaintCommentId);
        });
    }

    private Maint createMaint() {
        final var maintEntityRequest = Maint.builder()
                .maintIdentifier(format("MAINT-%s", FAKER.number().digits(10)))
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueDate(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit()))
                .client(FAKER.company().name())
                .build();

        return maintRepository.save(maintEntityRequest);
    }
}
