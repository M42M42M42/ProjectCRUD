package integration.mapper;

import com.m42.custody.entity.ProdEntity;
import com.m42.custody.mapper.ProdMapper;
import integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdMapperIT extends BaseIntegrationTest {
    @Autowired
    private ProdMapper prodMapper;

    @Test
    void testSelectAll() {
        List<ProdEntity> list = prodMapper.selectList(null);
        assertThat(list).isNotNull();
    }
}
