package com.example.maintmanagerultimate;

import com.github.javafaker.Faker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Locale;

@SpringBootTest
public class MaintManagerUltimateApplicationTests {

    protected static final String MAINT_BODY_SHOULD_NOT_BE_NULL = "Maint body should not be null !!";
    protected static final Faker FAKER = new Faker(Locale.ENGLISH);

    protected final TestRestTemplate testRestTemplate = new TestRestTemplate();
}
