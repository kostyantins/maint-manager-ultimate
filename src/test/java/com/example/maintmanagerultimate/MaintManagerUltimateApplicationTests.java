package com.example.maintmanagerultimate;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
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

    public static final PostgreSQLContainer postgresqlContainer;

    //Just an example
//    @Autowired
//    protected JdbcTemplate jdbcTemplate;

    static {
        postgresqlContainer = new PostgreSQLContainer("postgres:15.2")
                .withDatabaseName(DATABASE_NAME)
                .withUsername(DATABASE_USER_NAME)
                .withPassword(DATABASE_USER_PASSWORD);

        postgresqlContainer.start();
    }

//    @AfterEach
//    void tearDownBase() {
//        deleteFromTables(jdbcTemplate, "om_user");
//    }

    @Test
    void validIfContainerIsRunning() {
        Assertions.assertTrue(postgresqlContainer.isRunning());
    }
}

