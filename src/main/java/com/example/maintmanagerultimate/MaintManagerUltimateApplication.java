package com.example.maintmanagerultimate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MaintManagerUltimateApplication {

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
}
