package com.example.maintmanagerultimate;

import com.example.maintmanagerultimate.presentation.controller.MaintCommentsController;
import com.example.maintmanagerultimate.presentation.controller.MaintController;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableCaching
@RequiredArgsConstructor
@SpringBootApplication
public class MaintManagerUltimateApplication {

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

    //Uncomment bloc for catch testing
//    @PostConstruct
//    public void createDefaultCapabilities() {
//        var maintBody = Maint.builder()
//                .maintIdentifier("MAINT-1")
//                .capabilityId(APPROVALS)
//                .createdData(now())
//                .dueData(now())
//                .solvePriorityId(HIGH)
//                .fixVersion("1.1.1")
//                .client("MCB")
//                .build();
//
//        final var maint = maintController.createMaint(maintBody);
//
//        maintBody.setId(1L);
//
//        IntStream.rangeClosed(0, 1000)
//                .forEach(i -> {
//                    var comment = MaintComments.builder()
//                            .maint(maintBody)
//                            .commentText("New comment" + i)
//                            .createdData(now())
//                            .build();
//
//                    maintCommentsController.createComment(comment);
//                });
//    }
}
