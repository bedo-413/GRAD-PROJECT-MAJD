package jordan.university.gradproject2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GradProject2ApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        // This test will verify that the application context loads successfully
        // with the configured database settings
    }

    @Test
    void testDatabaseConnection() throws Exception {
        // Verify database connection
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database connection successful!");
            System.out.println("Database: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());

            // Get all tables created by Liquibase
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            List<String> tableNames = new ArrayList<>();
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
                System.out.println("Found table: " + tableName);
            }

            // Verify that expected tables exist
            assertTrue(tableNames.contains("USERS"), "Users table should exist");
            assertTrue(tableNames.contains("ACTIVITY_FORMS"), "Activity forms table should exist");
            assertTrue(tableNames.contains("LOCATIONS"), "Locations table should exist");
        }
    }
}
