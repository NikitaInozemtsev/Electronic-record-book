package configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseDbTestClass {

    public static final PostgreSQLContainer postgreSQLContainer;
    private static final String IMAGE_VERSION = "postgres:14.0";

    static {
        postgreSQLContainer = new PostgreSQLContainer(IMAGE_VERSION);
        postgreSQLContainer.start();
        datasourceConfig();
    }


    static void datasourceConfig() {
        System.setProperty("DB_URL", postgreSQLContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgreSQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", postgreSQLContainer.getPassword());
    }

}

