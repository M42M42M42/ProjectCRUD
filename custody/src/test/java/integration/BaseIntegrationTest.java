package integration;

import com.m42.custody.CustodyApplication;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = CustodyApplication.class)
public class BaseIntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("custody-test")
            .withUsername("test")
            .withPassword("test");

    static {
        mysql.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @AfterEach
    void globalCleanUp() {
        jdbcTemplate.execute("DELETE FROM t_prod_base_info");
        jdbcTemplate.execute("DELETE FROM t_product_msg");
    }
}
