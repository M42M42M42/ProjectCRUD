package integration;

import com.m42.custody.entity.ProdEntity;
import com.m42.custody.mapper.ProdMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
public class ProdMapperIT {
    @Container
    private static final MySQLContainer mysql = new MySQLContainer("mysql:8.0");

    @Autowired
    private ProdMapper prodMapper;

    @Test
    void testSelectAll() {
        List<ProdEntity> list = prodMapper.selectList(null);
        assertNotNull(list);
    }
}
