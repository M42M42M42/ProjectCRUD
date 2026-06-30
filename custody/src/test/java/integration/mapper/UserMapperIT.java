package integration.mapper;

import com.m42.custody.entity.UserEntity;
import com.m42.custody.mapper.UserMapper;
import integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserMapperIT extends BaseIntegrationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void should_insert_user_success() {
        UserEntity user = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();

        int rows = userMapper.insertUser(user);
        Long id = user.getId();

        assertThat(rows).isEqualTo(1);
        assertThat(id).isNotNull(); // 测试自动回填主键 id
    }

    @Test
    void should_insert_user_fail_when_email_duplicate() {
        UserEntity user1 = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("test_user_002")
                .password("123456")
                .email("test_user_001@test.com")
                .build();

        userMapper.insertUser(user1);
        assertThatThrownBy(() -> userMapper.insertUser(user2))
                .isInstanceOf(DuplicateKeyException.class);
        // TODO: 写好查询接口后，增加“判断 user1 插入成功、user2 插入失败”的逻辑
    }

    @Test
    void should_select_user_list_by_username_success() {
        UserEntity user1 = UserEntity.builder()
                .username("duplicated_username")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("duplicated_username")
                .password("123456")
                .email("test_user_002@test.com")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("test_user_003")
                .password("123456")
                .email("test_user_003@test.com")
                .build();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);
        UserEntity userToQuery = UserEntity.builder()
                .username("duplicated")
                .build();

        List<UserEntity> result = userMapper.selectUserList(userToQuery);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void should_select_user_list_by_email_success() {
        UserEntity user1 = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("test_user_002")
                .password("123456")
                .email("test_user_002@test.com")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("test_user_003")
                .password("123456")
                .email("test_user_003@test.com")
                .build();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);
        UserEntity userToQuery = UserEntity.builder()
                .email("test_user_003@test.com")
                .build();

        List<UserEntity> result = userMapper.selectUserList(userToQuery);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getUsername()).isEqualTo("test_user_003");
    }
}
