package com.example.maintmanagerultimate;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MaintManagerUltimateApplicationTests {

    protected static final String MAINT_BODY_SHOULD_NOT_BE_NULL = "Maint body should not be null !!";
    protected static final Faker FAKER = new Faker(Locale.ENGLISH);

    private static final String DATABASE_NAME = "maint-manager-test";
    private static final String DATABASE_USER_NAME = "postgres";
    private static final String DATABASE_USER_PASSWORD = "postgres";

    @LocalServerPort
    private int port;

    protected final RestTemplate restTemplate = new RestTemplate();

    protected String absoluteUrl(String path) {
        return "http://localhost:" + port + path;
    }

    private static PostgreSQLContainer postgresqlContainer;

    static {
        postgresqlContainer = new PostgreSQLContainer("postgres:15.2")
                .withDatabaseName(DATABASE_NAME)
                .withUsername(DATABASE_USER_NAME)
                .withPassword(DATABASE_USER_PASSWORD);

        postgresqlContainer.start();
    }

    //Just an example
//    @Autowired
//    protected JdbcTemplate jdbcTemplate;


//    @AfterEach
//    void tearDownBase() {
//        deleteFromTables(jdbcTemplate, "om_user");
//    }

    @DynamicPropertySource
    public static void containersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    }

    @Test
    void validIfContainerIsRunning() {
        Assertions.assertTrue(postgresqlContainer.isRunning());
    }
}

