package com.example.maintmanagerultimate;

import com.github.javafaker.Faker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MaintManagerUltimateApplicationTests {

    protected static final String MAINT_BODY_SHOULD_NOT_BE_NULL = "Maint body should not be null !!";
    protected static final Faker FAKER = new Faker(Locale.ENGLISH);

    @LocalServerPort
    private int port;

    protected final RestTemplate restTemplate = new RestTemplate();

    protected String absoluteUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
