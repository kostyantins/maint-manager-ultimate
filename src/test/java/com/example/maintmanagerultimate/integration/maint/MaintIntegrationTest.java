package com.example.maintmanagerultimate.integration.maint;

import com.example.maintmanagerultimate.MaintManagerUltimateApplicationTests;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.persistence.repositories.MaintRepository;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MaintIntegrationTest extends MaintManagerUltimateApplicationTests {

    @Autowired
    private MaintController maintService;
    @Autowired
    private MaintRepository maintRepository;

    @Test
    void testMaintShouldBeCreated() {
        final var maintRequest = createRequestMaintModel();

        final var expectedMaintId = requireNonNull(maintService.createMaint(maintRequest)
                .getBody(), "Maint body should not be null !!").getMaintId();

        final var actualMaintId = requireNonNull(maintRepository.findById(expectedMaintId)
                .orElseThrow(() -> new NoSuchMaintException(expectedMaintId)))
                .getId();

        assertThat(actualMaintId).isEqualTo(expectedMaintId);
    }

    private Maint createRequestMaintModel() {
        return Maint.builder()
                .maintIdentifier("MAINT-111")
                .capabilityId(Capabilities.APPROVALS)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion("1.1.1")
                .client("MCB")
                .build();
    }
}
