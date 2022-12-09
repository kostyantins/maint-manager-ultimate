package com.example.maintmanagerultimate;

import com.example.maintmanagerultimate.persistence.entities.Capability;
import com.example.maintmanagerultimate.persistence.entities.Maint;
import com.example.maintmanagerultimate.persistence.entities.MaintComments;
import com.example.maintmanagerultimate.persistence.entities.Priorities;
import com.example.maintmanagerultimate.persistence.enums.CapabilityNames;
import com.example.maintmanagerultimate.persistence.enums.PrioritiesNames;
import com.example.maintmanagerultimate.presenttation.controller.CapabilityController;
import com.example.maintmanagerultimate.presenttation.controller.MaintCommentsController;
import com.example.maintmanagerultimate.presenttation.controller.MaintController;
import com.example.maintmanagerultimate.presenttation.controller.PriorityController;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.LocalDate.now;

@RequiredArgsConstructor
@SpringBootApplication
public class MaintManagerUltimateApplication {

    private final CapabilityController capabilityController;
    private final PriorityController priorityController;
    private final MaintController maintController;
    private final MaintCommentsController maintCommentsController;

    public static void main(String[] args) {
        SpringApplication.run(MaintManagerUltimateApplication.class, args);
    }

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(
                new Info()
                        .title("Maint manager API")
                        .description("Maint manager system Swagger Open API")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Groot")
                                .email("imgroot@gmail.com")
                                .url("https://www.marvel.com/teams-and-groups/guardians-of-the-galaxy"))
                        .license(new License()
                                .name("Guardians of the Galaxy")
                                .identifier("0123-mnj1-njnc-74y3-194d-ir39")
                                .url("https://springdoc.org/")));
    }

    @PostConstruct
    public void createDefaultCapabilities() {
        Stream.of(
                        Capability.builder()
                                .id(1L)
                                .capabilityName(CapabilityNames.ACCESS_CONTROL)
                                .build(),
                        Capability.builder()
                                .id(2L)
                                .capabilityName(CapabilityNames.APPROVALS)
                                .build(),
                        Capability.builder()
                                .id(3L)
                                .capabilityName(CapabilityNames.LIMITS)
                                .build()
                )
                .forEach(capabilityController::createCapability);

        Stream.of(
                        Priorities.builder()
                                .id(1L)
                                .priorityName(PrioritiesNames.HIGH)
                                .build(),
                        Priorities.builder()
                                .id(2L)
                                .priorityName(PrioritiesNames.MID)
                                .build(),
                        Priorities.builder()
                                .id(3L)
                                .priorityName(PrioritiesNames.LOW)
                                .build()

                )
                .forEach(priorityController::createPriority);

        var maintBody = Maint.builder()
                .maintIdentifier("MAINT-1")
                .capabilityId(1L)
                .createdData(now())
                .dueData(now())
                .solvePriorityId(1)
                .fixVersion("1.1.1")
                .client("MCB")
                .build();

        final var maint = maintController.createMaint(maintBody);

        maintBody.setId(1L);

        IntStream.rangeClosed(0, 1000)
                .forEach(i -> {
                    var comment = MaintComments.builder()
                            .maint(maintBody)
                            .commentText("New comment" + i)
                            .createdData(now())
                            .build();

                    maintCommentsController.createComment(comment);
                });
    }
}
