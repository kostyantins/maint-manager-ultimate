package com.example.maintmanagerultimate.integration.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
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

@RequiredArgsConstructor
public class MaintIntegrationTest extends MaintManagerUltimateApplicationTests {

    private static final String MAINT_BODY_SHOULD_NOT_BE_NULL = "Maint body should not be null !!";
    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    @Autowired
    private MaintController maintService;
    @Autowired
    private MaintRepository maintRepository;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var expectedMaintId = requireNonNull(maintService.createMaint(maintRequest)
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getMaintId();

        final var actualMaintId = requireNonNull(maintRepository.findById(expectedMaintId)
                .orElseThrow(() -> new NoSuchMaintException(expectedMaintId)))
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaintId);
    }

    //TODO ask about failure
    @Test
    void testMaintShouldBeRetrievedById() {
        final var maintRequest = createRequestMaintModel();

        final var expectedMaintId = maintRepository.save(maintRequest).getId();

        final var actualMaintId = requireNonNull(maintService.getMaint(expectedMaintId)
                .getBody(), MAINT_BODY_SHOULD_NOT_BE_NULL)
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaintId);
    }

    private Maint createRequestMaintModel() {
        return Maint.builder()
                .maintIdentifier(format("MAINT-%s", FAKER.number().digits(10)))
                .capabilityId(Capabilities.APPROVALS)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit()))
                .client(FAKER.company().name())
                .build();
    }
}
