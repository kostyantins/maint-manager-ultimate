package com.example.maintmanagerultimate.unit;

import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.enums.Capabilities;
import com.example.maintmanagerultimate.persistence.enums.Priorities;
import com.example.maintmanagerultimate.service.dto.CreateMaintRequestDto;
import com.github.javafaker.Faker;

import java.util.Locale;

import static java.lang.String.format;
import static java.time.LocalDate.now;

public class UnitTestBase {

    protected static final Faker FAKER = new Faker(Locale.ENGLISH);
    protected static final String MAINT_IDENTIFIER = format("MAINT-%s", FAKER.number().digits(10));
    protected static final String MAINT_FIX_VERSION = format("%s.%s.%s", FAKER.number().digit(), FAKER.number().digit(), FAKER.number().digit());

    protected Maint createMaintModel() {
        return Maint.builder()
                .id(1L)
                .maintIdentifier(MAINT_IDENTIFIER)
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueDate(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(MAINT_FIX_VERSION)
                .client(FAKER.company().name())
                .build();
    }

    protected CreateMaintRequestDto createRequestMaintModel() {
        return CreateMaintRequestDto.builder()
                .maintIdentifier(MAINT_IDENTIFIER)
                .capabilityId(Capabilities.APPROVALS)
                .createdDate(now())
                .dueDate(now())
                .solvePriorityId(Priorities.HIGH)
                .fixVersion(MAINT_FIX_VERSION)
                .client(FAKER.company().name())
                .build();
    }
}
